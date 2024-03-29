package app.rest;

import app.exceptions.AuthenticationException;
import app.exceptions.AuthorizationException;
import app.exceptions.TwofactorSetup;
import app.exceptions.UserNotFoundException;
import app.models.Country;
import app.models.User.User;
import app.repositories.CountryRepository;
import app.repositories.JPAUserRepository;
import app.response.LoginResponse;
import app.security.JWTokenInfo;
import app.security.JWTokenUtils;
import app.service.Stripe.StripeService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stripe.exception.StripeException;
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
import java.util.List;
import java.util.Objects;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

/**
 * Set of endpoints used to sign-up and sign-in users
 *
 * @author Pepijn dik
 */
@RestController
public class AuthController {

    @Autowired
    private JWTokenUtils tokenGenerator;

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

    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    private StripeService stripeService;

    /**
     * Endpoint used to sign up a user
     *
     * @param signupInfo The user to sign-up
     * @return The signed-up user
     */
    @PostMapping("/auth/users")
    public ResponseEntity<Object> createUser(@RequestBody ObjectNode signupInfo) {

        String email = signupInfo.get("email") == null ? null : signupInfo.get("email").asText();
        String name = signupInfo.get("name") == null ? null : signupInfo.get("name").asText();
        String givenPassword = signupInfo.get("password") == null ? null : signupInfo.get("password").asText();
        String type = signupInfo.get("type") == null ? null : signupInfo.get("type").asText();
        Integer countryId = signupInfo.get("country") == null ? null : signupInfo.get("country").asInt();

        //Get Country by ID
        Country c = countryRepo.findById(countryId);
        //Set Country
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        try {
            user.setStripeId(stripeService.createCustomer(user).getId());
        } catch (StripeException e) {
            System.out.printf("Error creating stripe customer: %s", e.getMessage());
        }

        user.setEncodedPassword(user.hashPassword(givenPassword));
        if (Objects.equals(type, "BUSINESS")) {
            user.setType(User.Type.BUSINESS);
        } else {
            user.setType(User.Type.PERSON);
        }
        if (c != null) {
            user.setCountry(c);
        }
        user.setCountryKey(countryId);
        user.setCreatedAt(LocalDateTime.now());
        LoginResponse loginResponse = new LoginResponse();
        try {
            User savedUser = userRepo.save(user);
            loginResponse.setMe(savedUser);
            URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/{id}").
                buildAndExpand(savedUser.getId()).toUri();
            return ResponseEntity.created(location).body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new UserNotFoundException("Could not create user", e));
        }
    }

    /**
     * Refresh the JWT token
     *
     * @param request The request
     * @param response The response
     * @return The new JWT token
     * @throws AuthenticationException If the token is invalid
     */
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

    /**
     * Verify the 2FA code
     *
     * @param provided The provided code
     * @param tokenInfo The token info
     * @return The user
     */
    @PostMapping("/auth/2fa/verify")
    @ResponseBody
    public ResponseEntity<?> verify(@RequestBody ObjectNode provided, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo) {
        // secret is fetched from some storage
        User user = tokenInfo.getUser();
        String code = "";
        try {
            code = provided.get("code").asText();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthorizationException("No code provided"));
        }

        String finalCode = code;
        Object body = new Object() {
            public final boolean success = verifier.isValidCode(user.getSecret(), finalCode);
            public final String message = success ? "Code is valid" : "Code is invalid";
            public final String code = finalCode;
        };
        // verify the code
        if (verifier.isValidCode(user.getSecret(), code)) {
            return ResponseEntity.accepted().body(body);
        }
        return ResponseEntity.accepted().body(body);
    }


    /**
     * Get the current logged-in user
     *
     * @param tokenInfo The token info
     * @return The user
     */
    @GetMapping("/auth/me")
    public ResponseEntity<User> getUser(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo) {
        if (tokenInfo.getUser() == null) {
            throw new AuthorizationException("User not found");
        }
        return ResponseEntity.ok(tokenInfo.getUser());
    }


    /**
     * Setup 2FA for the user and return the QR code
     *
     * @param tokenInfo The token info
     * @return body
     */
    @PostMapping("/auth/2fa/setup")
    public ResponseEntity<Object> setupDevice(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo tokenInfo) throws QrGenerationException, TwofactorSetup {
        if (tokenInfo.twoFactorEnabled()) {
            throw new TwofactorSetup("Twofactor already enabled");
        }
        User user = tokenInfo.getUser();

        // Generate and store the secret
        String secret = secretGenerator.generate();
        user.setSecret(secret);
        user.setTwoFactorEnabled(true);
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


    /**
     * Login a user
     *
     * @param signOnInfo The sign on info
     * @param request The request
     * @param response The response
     * @return LoginResponse
     * @throws AuthenticationException authentication failed
     */
    @PostMapping(path = "/auth", produces = "application/json")
    public ResponseEntity<LoginResponse> authenticateUser(
        @RequestBody ObjectNode signOnInfo,
        HttpServletRequest request,
        HttpServletResponse response)
        throws AuthenticationException {

        String userEmail = signOnInfo.get("email").asText();
        String password = signOnInfo.get("password").asText();


        // Authenticate the user using the credentials provided
        List<User> users = userRepo.findByEmail(userEmail);
        User user = users.size() > 0 ? users.get(0) : null;
        System.out.println(users);
        if (user == null || !user.verifyPassword(password)) {
            throw new AuthenticationException("Invalid user and/or password");
        }


        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMe(user);
        URI location = null;
        String tokenString = tokenGenerator.encode(user.getId());
        if (user.isTwoFactorEnabled()) {
            System.out.println("2FA enabled");
            loginResponse.setNeedTwoFactor(true);
            location = ServletUriComponentsBuilder.fromPath("/auth/2fa/verify").build().toUri();
        } else {
            location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        }
        return ResponseEntity.accepted().location(location).header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString).body(loginResponse);
    }
}
