package app;

import app.server.CustomBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    public static class APIConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .exposedHeaders("Authorization")
                    .allowedOriginPatterns("http://localhost/api/*")
                    .allowedOriginPatterns("http://localhost:*")
                    .allowedOriginPatterns("https://sumting.pdik.nl/api/*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
        }
    }
}
