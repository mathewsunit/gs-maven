package com.jspBay.controller;

import com.jspBay.DTO.UserDTO;
import com.jspBay.domain.User;
import com.jspBay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sunit on 3/21/17.
 */

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User createAccount(@RequestBody UserDTO newUserDTO) {
        User newUser = userService.createNewUser(newUserDTO);
        return newUser;
    }
}