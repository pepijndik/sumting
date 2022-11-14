package app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import app.exceptions.UserNotFoundException;
import app.models.User.User;
import app.repositories.JPAUserRepository;
import app.exceptions.AuthenticationException;
import app.security.JWTokenInfo;
import app.security.JWTokenUtils;
import app.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.time.LocalDateTime;

/**
 * Set of endpoints used to sign-up and sign-in users
 * <p>
 * Author: Pepijn dik
 */
@RestController
public class AuthController {

    @Autowired
    private JWTokenUtils tokenGenerator;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JPAUserRepository userRepo;

    @Autowired
    private JWTokenUtils tokenUtils;

    @PostMapping("/auth/users")
    public ResponseEntity<Object> createUser(@RequestBody ObjectNode signupInfo) {

        String email = signupInfo.get("email") == null ? null : signupInfo.get("email").asText();
        String name = signupInfo.get("name") == null ? null : signupInfo.get("name").asText();
        String givenPassword = signupInfo.get("password") == null ? null : signupInfo.get("password").asText();
        Integer countryId = signupInfo.get("country") == null ? null : signupInfo.get("country").asInt();

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setEncodedPassword(encoder.encode(givenPassword));
        user.setType(User.Type.PERSON);
        user.setCountry(countryId);
        user.setCreatedAt(LocalDateTime.now());
        try {
            User savedUser = userRepo.save(user);
            URI location = ServletUriComponentsBuilder.
                    fromCurrentRequest().path("/{id}").
                    buildAndExpand(savedUser.getId()).toUri();
            return ResponseEntity.created(location).body(user);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new UserNotFoundException("Could not create user",e));
        }
    }

    @PostMapping(path = "/auth/refresh-token", produces = "application/json")
    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String encodedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (encodedToken == null) {
            // avoid giving clues to the caller (do not say that header is not present, for example)
            throw new AuthenticationException("authentication problem");
        }

        // remove the bearer initial string
        encodedToken = encodedToken.replace("Bearer ", "");

        // get a representation of the token
        JWTokenInfo tokenInfo = tokenUtils.decode(encodedToken, true);

        // Check if the token can be refreshed (You can also check if the user or the token was blacklisted)
        if (!tokenUtils.isRenewable(tokenInfo)) {
            throw new AuthenticationException("Token is not renewable");
        }

        // refresh the token for the user
        String tokenString = tokenGenerator.encode(tokenInfo.getEmail());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString).build();
    }

    @PostMapping(path = "/auth", produces = "application/json")
    public ResponseEntity<Object> authenticateUser(
            @RequestBody ObjectNode signOnInfo,
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {

        String userEmail = signOnInfo.get("email").asText();
        String password = signOnInfo.get("password").asText();

        // Authenticate the user using the credentials provided
        User user = userRepo.findByEmail(userEmail);

        if (user == null) {
            throw new AuthenticationException("Invalid user and/or password");
        }

        String encodedPassword = encoder.encode(password);

        if (!user.validateEncodedPassword(encodedPassword)) {
            throw new AuthenticationException("Invalid user and/or password");
        }

        // Issue a token for the user valid for some time
        String tokenString = tokenGenerator.encode(user.getEmail());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        Object body = new Object() {
            public final User me = user;
        };

        return ResponseEntity.accepted().location(location).header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString).body(body);
    }
}
