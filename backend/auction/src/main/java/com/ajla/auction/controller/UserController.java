package com.ajla.auction.controller;

import com.ajla.auction.config.JwtTokenUtil;
import com.ajla.auction.model.JwtResponse;
import com.ajla.auction.model.User;
import com.ajla.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    // Using the Spring Authentication Manager, we authenticate the username and password.
    private final AuthenticationManager authenticationManager;
    //for generate or validate token
    private final JwtTokenUtil jwtTokenUtil;

    //dependency injection
    @Autowired
    public UserController(final UserService userService, final AuthenticationManager authenticationManager,
    final JwtTokenUtil jwtTokenUtil){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        //when we have 3 arg, first is response, second is headers, and third is status
       return ResponseEntity.ok(new JwtResponse(userDetails.getUsername(), token));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> saveUserData(@RequestBody final User user){
        return userService.saveDataFromUser(user);
    }
}

