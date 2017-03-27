package com.jspBay;

import com.jspBay.domain.User;
import com.jspBay.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {

            //save a couple of players
            userRepository.save(new User("ala", "ala@ala.com", new BCryptPasswordEncoder().encode("ala")));
            userRepository.save(new User("mary", "mary@mary.com",  new BCryptPasswordEncoder().encode("mary")));

        };
    }

}