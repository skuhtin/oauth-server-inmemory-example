package ua.skuhtin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class AuthExampleApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthExampleApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Auth2 in memory application started");
        SpringApplication.run(AuthExampleApplication.class, args);
    }
}
