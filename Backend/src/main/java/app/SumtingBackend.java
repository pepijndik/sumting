package app;

import app.Server.CustomBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SumtingBackend {

    public static void main(String[] args) {
            SpringApplication application = new SpringApplication(SumtingBackend.class);
            application.setBanner(new CustomBanner());
            application.run(args);
    }

    @Configuration
    public static class APIConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .exposedHeaders("Authorization")
                    .allowedOriginPatterns("http://localhost:*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
        }
    }
}
