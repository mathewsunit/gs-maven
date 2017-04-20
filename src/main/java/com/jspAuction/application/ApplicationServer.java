package com.jspAuction.application;

import com.jspAuction.application.configuration.AccountsConfiguration;
import com.jspAuction.application.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(AccountsConfiguration.class)
public class ApplicationServer {

    @Autowired
    protected ItemRepository itemRepository;

    protected Logger logger = Logger.getLogger(ApplicationServer.class.getName());

    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     *
     * @param args
     *            Program arguments - ignored.
     */
    public static void main(String[] args) {
        // Tell server to look for application-server.properties or
        // application-server.yml
        System.setProperty("spring.config.name", "application-server");

        SpringApplication.run(ApplicationServer.class, args);
    }
}
