package app.security;

import app.repositories.JPAUserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import app.exceptions.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

/**
 * A utility class used to encode and decode JWT tokens
 *
 * @author Pepijn dik
 */
@Component
public class JWTokenUtils {

    // A claim indicating if the user is an administrator
    public static final String JWT_USER_CLAIM = "user";

    @Autowired
    private JPAUserRepository userRepo;
    @Value("${jwt.issuer:MyOrganisation}")
    private String issuer;

    @Value("${jwt.pass-phrase}")
    private String passphrase;

    @Value("${jwt.expiration-seconds}")
    private int expiration;

    @Value("${jwt.refresh-expiration-seconds}")
    private int refreshExpiration;

    /**
     * Generate a Json Web Token
     * @param id user id (or subject)
     * @return the token representation
     */
    public String encode(Integer id) {

        Key key = getKey(passphrase);

        return Jwts.builder()
                .claim(Claims.SUBJECT,id) // registered claim
                .setIssuer(issuer) // registered claim
                .setIssuedAt(new Date()) // registered claim
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // registered claim
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Get the secret key
     * @param passPhrase the passphrase
     * @return the key
     */
    private Key getKey(String passPhrase) {
        byte[] hmacKey = passPhrase.getBytes(StandardCharsets.UTF_8);
        Key key = new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
        return key;
    }

    /**
     * Decode the given token, returning an object with useful token data
     * @param encodedToken the token
     * @param expirationLenient if true, the expiration date is ignored
     * @return the token data
     * @throws AuthenticationException for expired, malformed tokens
     */
    public JWTokenInfo decode(String encodedToken, boolean expirationLenient) throws AuthenticationException {
        try {
            // Validate the token
            Key key = getKey(passphrase);

            Jws<Claims> jws = Jwts.parserBuilder().
                    setSigningKey(passphrase.getBytes()).
                    build().
                    parseClaimsJws(encodedToken);

            Claims claims = jws.getBody();

            return generateTokenInfo(claims);

        } catch (MalformedJwtException |
                 UnsupportedJwtException | IllegalArgumentException| SignatureException e) {
            throw new AuthenticationException(e.getMessage());
        } catch(ExpiredJwtException e) {
            if(!expirationLenient) {
                throw new AuthenticationException(e.getMessage());
            } else {
                return generateTokenInfo(e.getClaims());
            }
        }
    }

    private JWTokenInfo generateTokenInfo(Claims claims) {

        JWTokenInfo tokenInfo = new JWTokenInfo();

        tokenInfo.setId(Integer.parseInt(claims.getSubject()));
        tokenInfo.setUser(userRepo.findById(tokenInfo.getId()));

        tokenInfo.setIssuedAt(claims.getIssuedAt());
        tokenInfo.setExpiration(claims.getExpiration());

        return tokenInfo;

    }

    public boolean isRenewable(JWTokenInfo tokenInfo) {

        // If token is still valid there is no reason to issue a new one
        if(tokenInfo.getExpiration().compareTo(new Date()) > 0) {
            return false;
        }

        // Calculating the refresh time limit
        Calendar cal = Calendar.getInstance();
        cal.setTime(tokenInfo.getIssuedAt());
        cal.add(Calendar.SECOND,refreshExpiration);

        System.out.println("max refresh: " + cal.getTime());
        System.out.println("current date: " + new Date());

        // max refresh time should be greater than current time
        return cal.getTime().compareTo(new Date()) > 0;
    }

}

