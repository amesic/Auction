package com.ajla.auction.controller;

import com.ajla.auction.config.JwtTokenUtil;
import com.ajla.auction.model.JwtResponse;
import com.ajla.auction.model.User;
import com.ajla.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"})
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    // Using the Spring Authentication Manager, we authenticate the username and password.
    private final AuthenticationManager authenticationManager;
    //for generate or validate token
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(final UserService userService,
                          final AuthenticationManager authenticationManager,
                          final JwtTokenUtil jwtTokenUtil) {
        Objects.requireNonNull(userService, "userService must not be null.");
        this.userService = userService;
        Objects.requireNonNull(authenticationManager, "authenticationManager must not be null.");
        this.authenticationManager = authenticationManager;
        Objects.requireNonNull(jwtTokenUtil, "jwtTokenUtil must not be null.");
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final User authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        //when we have 3 arg, first is response, second is headers, and third is status
       return ResponseEntity.ok(new JwtResponse(userDetails.getUsername(), token));
    }

    private void authenticate(final String email, final String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> saveUserData(@RequestBody final User user) {
        Boolean emailExist = userService.saveDataFromUser(user);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        if(!emailExist) {
            return new ResponseEntity<>("You are successfully registered " + user.getUserName() + "!", headers, HttpStatus.OK);
        }
        return new ResponseEntity<>("You are already registered with " + user.getEmail() + " email!", headers, HttpStatus.BAD_REQUEST);
    }
}

