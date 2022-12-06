package app.server.Stripe;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Configuration

@ConfigurationProperties(prefix = "stripe")
@Getter
@Setter
public class StripeConfig {

    @NotBlank
    private String secret;
    @NotBlank
    private String mode;

    private String webhookUrl = "https://localhost:8083/stripe/webhooks";
    @NotBlank
    private String webhooksecret;
    public String getSecretKey() {
        return secret;
    }


    public boolean isLive(){
        return Objects.equals(mode, "live");
    }


    public boolean isTest(){
        return Objects.equals(mode, "test");
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public String getWebhookSecret() {
        return webhooksecret;
    }
}
