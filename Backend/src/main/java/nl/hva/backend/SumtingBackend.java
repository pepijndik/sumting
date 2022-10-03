package nl.hva.backend;

import nl.hva.backend.Server.CustomBanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.PrintStream;

@SpringBootApplication
public class SumtingBackend {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SumtingBackend.class);
        application.setBanner(new CustomBanner());
        application.run(args);
    }
}
