package io.github.seonrizee.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Level2Application {

    public static void main(String[] args) {
        SpringApplication.run(Level2Application.class, args);
    }

}
