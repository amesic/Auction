package com.ajla.auction.controller;

import com.ajla.auction.model.User;
import com.ajla.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {
    //properties
    private final UserService userService;

    //dependency injection
    @Autowired
    public UserController(final UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/login", consumes = MediaType.ALL_VALUE)
    public Long getUserId(@RequestBody final User user){
        return userService.findByEmailPassword(user.getEmail(), user.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<String> saveUserData(@RequestBody final User user){
        return userService.saveDataFromUser(user);
    }
}

