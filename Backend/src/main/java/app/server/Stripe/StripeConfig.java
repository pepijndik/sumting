package app.server.Stripe;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * The main configuration class for the backend
 */
@Configuration
@ConfigurationProperties(prefix = "stripe")
@Getter
@Setter
public class StripeConfig {

    @NotBlank
    private String secret;
    @NotBlank
    private String mode;

    @NotBlank
    private String webhook = "https://localhost:8083/stripe/webhooks";
    @NotBlank
    private String webhooksecret;

    /**
     * This method returns the secret key
     *
     * @return the secret key
     */
    public String getSecretKey() {
        return secret;
    }

    /**
     * This method checks if the mode is live
     *
     * @return true if the mode is live
     */
    public boolean isLive() {
        return Objects.equals(mode, "live");
    }

    /**
     * This method checks if the mode is test
     *
     * @return true if the mode is test
     */
    public boolean isTest() {
        return Objects.equals(mode, "test");
    }

    /**
     * Gets the current webhook url
     *
     * @return the webhook url
     */
    public String getWebhookUrl() {
        return webhook;
    }

    /**
     * Sets the webhook secret
     *
     * @param webhook the webhook secret
     */
    public void setWebhookSecret(String webhook) {
        webhooksecret = webhook;
    }

    /**
     * Gets the current webhook secret.
     * @return The current webhook secret.
     */
    public String getWebhookSecret() {
        return webhooksecret;
    }
}
