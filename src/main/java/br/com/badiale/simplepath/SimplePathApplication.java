package br.com.badiale.simplepath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Bootstrap da aplicação.
 */
@SpringBootApplication
@Configuration
@Import(ApplicationConfiguration.class)
public class SimplePathApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimplePathApplication.class, args);
    }
}
