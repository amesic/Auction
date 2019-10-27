package com.ajla.auction.controller;

import com.ajla.auction.model.User;
import com.ajla.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping(value="/login", consumes = MediaType.ALL_VALUE)
    public Long getUserId(@RequestBody User user){
        return service.findByEmailPassword(user.getEmail(), user.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<String> saveUserData(@RequestBody User user){
        return service.saveDataFromUser(user);
    }
}

