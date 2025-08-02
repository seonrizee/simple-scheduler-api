package io.github.seonrizee.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Level5Application {

    public static void main(String[] args) {
        SpringApplication.run(Level5Application.class, args);
    }

}
