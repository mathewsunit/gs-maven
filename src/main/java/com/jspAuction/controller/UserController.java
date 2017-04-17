package com.jspAuction.controller;

import com.jspAuction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by sunit on 3/21/17.
 */

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        System.out.println("user");
        System.out.println(user);
        return user;
    }

    @RequestMapping("/resource")
    public Map<String, Object> home() {
        System.out.println("resource");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }
}