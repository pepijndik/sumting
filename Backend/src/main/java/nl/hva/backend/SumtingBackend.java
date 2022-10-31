package nl.hva.backend;

import nl.hva.backend.Server.CustomBanner;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.PrintStream;

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
