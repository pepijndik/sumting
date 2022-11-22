package app.rest;

import app.exceptions.AuthorizationException;
import app.exceptions.TwofactorSetup;
import app.views.UserView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;
import app.exceptions.UserNotFoundException;
import app.models.User.User;
import app.repositories.JPAUserRepository;
import app.exceptions.AuthenticationException;
import app.security.JWTokenInfo;
import app.security.JWTokenUtils;
import app.security.PasswordEncoder;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrDataFactory;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.time.LocalDateTime;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

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

    @Autowired
    private SecretGenerator secretGenerator;

    @Autowired
    private QrDataFactory qrDataFactory;

    @Autowired
    private QrGenerator qrGenerator;

    @Autowired
    private CodeVerifier verifier;

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
            return ResponseEntity.internalServerError().body(new UserNotFoundException("Could not create user", e));
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
        String tokenString = tokenGenerator.encode(tokenInfo.getId());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString).build();
    }

    @PostMapping("/auth/2fa/verify")
    @ResponseBody
    public String verify(@RequestParam String code,@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo) {
        // secret is fetched from some storage
        User user = tokenInfo.getUser();

        // verify the code
        if (verifier.isValidCode(user.getSecret(), code)) {
            return "CORRECT CODE";
        }

        return "INCORRECT CODE";
    }


    @GetMapping("/auth/me")
    public ResponseEntity<User> getUser(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo){
        if(tokenInfo.getUser() == null){
            throw new AuthorizationException("User not found");
        }
        return ResponseEntity.ok(tokenInfo.getUser());
    }


    @GetMapping("/auth/2fa/setup")
    public ResponseEntity<Object> setupDevice(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo) throws QrGenerationException, TwofactorSetup {
        if(tokenInfo.twoFactorEnabled()) {
            throw new TwofactorSetup("Twofactor already enabled");
        }
       User user = tokenInfo.getUser();

        // Generate and store the secret
        String secret = secretGenerator.generate();
        user.setSecret(secret);
        userRepo.save(user);

        QrData data = qrDataFactory.newBuilder()
                .label(user.getEmail())
                .secret(secret)
                .issuer("Sumting")
                .build();

        // Generate the QR code image data as a base64 string which
        // can be used in an <img> tag:
        String qrCodeImage = getDataUriForImage(
                qrGenerator.generate(data),
                qrGenerator.getImageMimeType()
        );

        URI location = ServletUriComponentsBuilder.fromPath("/auth/2fa/verify").build().toUri();
        Object body = new Object() {
            public final int status = HttpStatus.OK.value();
            public final int account = user.getId();
            public final String qr_code = qrCodeImage;
        };

        return ResponseEntity.accepted().location(location).body(body);
    }


    @PostMapping(path = "/auth", produces = "application/json")
    public ResponseEntity<Object> authenticateUser(
            @RequestBody ObjectNode signOnInfo,
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {

        String userEmail = signOnInfo.get("email").asText();
        String password = signOnInfo.get("password").asText();
        String TOTP = null;
        try{
            TOTP = signOnInfo.get("totp").asText(); //One time code, generated by a authenticator app
        }catch (Exception e){

        }


        // Authenticate the user using the credentials provided
        User user = userRepo.findByEmail(userEmail);

        if (user == null) {
            throw new AuthenticationException("Invalid user and/or password");
        }

        String encodedPassword = encoder.encode(password);

        if (!user.validateEncodedPassword(encodedPassword)) {
            throw new AuthenticationException("Invalid user and/or password");
        }
        if (user.isTwoFactorEnabled() && TOTP == null || user.isTwoFactorEnabled() && !verifier.isValidCode(user.getSecret(), TOTP)) {
            throw new AuthenticationException("Two factor authentication is enabled, but the code is invalid or not given");
        }

        // Issue a token for the user valid for some time
        String tokenString = tokenGenerator.encode(user.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        Object body = new Object() {
            public final User me = user;
        };

        return ResponseEntity.accepted().location(location).header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString).body(body);
    }
}
