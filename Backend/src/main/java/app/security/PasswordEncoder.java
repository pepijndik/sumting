package app.security;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Yet another password encoder helper (uses SHA256)
 *
 * Author: Pepijn dik
 *
 */
@Component
public class PasswordEncoder {

    public String encode(String text) {
        return Hashing.sha256().hashString(text, StandardCharsets.UTF_8).toString();
    }
}
