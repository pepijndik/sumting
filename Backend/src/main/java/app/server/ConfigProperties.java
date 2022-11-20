package app.server;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {
    @Bean
    @ConfigurationProperties(prefix = "aws.s3")
    public AmazonConig amazonAccesKey() {
        return new AmazonConig();
    }
}
