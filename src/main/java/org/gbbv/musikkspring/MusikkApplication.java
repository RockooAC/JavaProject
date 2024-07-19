package org.gbbv.musikkspring;

import org.gbbv.musikkspring.dto.UserDto;
import org.gbbv.musikkspring.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MusikkApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusikkApplication.class, args);
    }
}