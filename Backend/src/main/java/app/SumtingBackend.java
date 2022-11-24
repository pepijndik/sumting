package app;

import app.server.CustomBanner;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.time.NtpTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.UnknownHostException;

@SpringBootApplication
public class SumtingBackend {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public  static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    public static void main(String[] args) {
            SpringApplication application = new SpringApplication(SumtingBackend.class);
            application.setBanner(new CustomBanner());
            application.run(args);
    }

    @Configuration
    @EnableConfigurationProperties
    public static class APIConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .exposedHeaders("Authorization")
                    .allowedHeaders("*")
                    .allowedOrigins("http://localhost:8080","http://localhost","http://localhost:8083","http://127.0.0.1","https://sumting.pdik.nl","http://sumting.pdik.nl/api")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
        }
        @Bean
        public HashingAlgorithm hashingAlgorithm() {
            return HashingAlgorithm.SHA1;
        }
    }
}
