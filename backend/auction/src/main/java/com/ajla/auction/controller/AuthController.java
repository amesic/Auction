package com.ajla.auction.controller;

import com.ajla.auction.config.JwtTokenUtil;
import com.ajla.auction.model.Bid;
import com.ajla.auction.service.BidService;
import com.ajla.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final BidService bidService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public AuthController(final BidService bidService,
                          final JwtTokenUtil jwtTokenUtil,
                          final UserService userService) {
        Objects.requireNonNull(userService, "userService must not be null.");
        Objects.requireNonNull(jwtTokenUtil, "jwtTokenUtil must not be null.");
        Objects.requireNonNull(bidService, "bidService must not be null.");
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bidService = bidService;
    }

    @PostMapping("/bid/newBid")
    public ResponseEntity<Bid> saveBidFromUser(@RequestBody final Bid bid, final HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        if (requestTokenHeader != "") {
            jwtToken = requestTokenHeader.substring(7);
            if (!jwtTokenUtil.getUsernameFromToken(jwtToken).equals(bid.getUser().getEmail())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
            final Bid savedBid = bidService.saveBidFromUser(bid.getProduct().getId(), bid.getUser().getEmail(), bid.getValue());
        if (savedBid == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(savedBid, HttpStatus.OK);
        }
    }

