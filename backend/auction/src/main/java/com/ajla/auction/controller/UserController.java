package com.ajla.auction.controller;

import com.ajla.auction.config.JwtTokenUtil;
import com.ajla.auction.model.Card;
import com.ajla.auction.model.JwtResponse;
import com.ajla.auction.model.User;
import com.ajla.auction.service.CardService;
import com.ajla.auction.service.StripeService;
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

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    // Using the Spring Authentication Manager, we authenticate the username and password.
    private final AuthenticationManager authenticationManager;
    //for generate or validate token
    private final JwtTokenUtil jwtTokenUtil;
    private final StripeService stripeService;
    private final CardService cardService;

    @Autowired
    public UserController(final UserService userService,
                          final AuthenticationManager authenticationManager,
                          final JwtTokenUtil jwtTokenUtil,
                          final StripeService stripeService,
                          final CardService cardService) {
        Objects.requireNonNull(userService, "userService must not be null.");
        Objects.requireNonNull(authenticationManager, "authenticationManager must not be null.");
        Objects.requireNonNull(jwtTokenUtil, "jwtTokenUtil must not be null.");
        Objects.requireNonNull(stripeService, "stripeService must not be null.");
        Objects.requireNonNull(cardService, "cardService must not be null.");
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.stripeService = stripeService;
        this.cardService = cardService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final User authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final  String userName = userService.findByEmail(authenticationRequest.getEmail()).getUserName();
        //when we have 3 arg, first is response, second is headers, and third is status
       return ResponseEntity.ok(new JwtResponse(userName, token, userDetails.getUsername()));
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
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        Boolean emailExist = userService.saveDataFromUser(user);
        if(emailExist == null) {
            return new ResponseEntity<>("Your data is not valid!", headers, HttpStatus.BAD_REQUEST);
        }
        if(!emailExist) {
            String idCustomer = stripeService.createCustomer(user.getEmail());
            Card savedCard = cardService.saveCustomerId(idCustomer);
            userService.saveCardId(savedCard, user.getEmail());
            return new ResponseEntity<>(
                    "You are successfully registered " + user.getUserName() + "!",
                    headers,
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                "You are already registered with " + user.getEmail() + " email!",
                headers,
                HttpStatus.BAD_REQUEST);
    }
}
