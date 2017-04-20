package com.jspAuction.web;

import com.jspAuction.application.domain.User;
import com.jspAuction.application.repository.UserRepository;
import com.jspAuction.web.configuration.ApplicationSecurity;
import com.jspAuction.web.controller.UserController;
import com.jspAuction.web.controller.WebItemsController;
import com.jspAuction.web.service.WebItemsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false)
public class WebServer {
    /**
     * URL uses the logical name of account-service - upper or lower case,
     * doesn't matter.
     */
    public static final String APPLICATION_SERVICE_URL = "http://APPLICATION-SERVICE";

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new ApplicationSecurity();
    }

    public static void main(String[] args) {
        // Tell server to look for web-server.properties or web-server.yml
        System.setProperty("spring.config.name", "web-server");
        SpringApplication.run(WebServer.class, args);
    }

    /**
     * The ItemService encapsulates the interaction with the micro-service.
     *
     * @return A new service instance.
     */
    @Bean
    public WebItemsService itemsService() {
        return new WebItemsService(APPLICATION_SERVICE_URL);
    }

    /**
     * Create the controller, passing it the {@link WebItemsService} to use.
     *
     * @return
     */
    @Bean
    public WebItemsController itemsController() {
        return new WebItemsController(itemsService());
    }

    @Bean
    public UserController homeController() {
        return new UserController();
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