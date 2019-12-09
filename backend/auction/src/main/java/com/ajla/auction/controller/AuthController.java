package com.ajla.auction.controller;

import com.ajla.auction.config.JwtTokenUtil;
import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.model.UserWatchProductId;
import com.ajla.auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final BidService bidService;
    private final JwtTokenUtil jwtTokenUtil;
    private final SimpMessagingTemplate template;
    private final ProductController productController;

    private Bid highestBid;

    @Autowired
    public AuthController(final BidService bidService,
                          final JwtTokenUtil jwtTokenUtil,
                          final SimpMessagingTemplate template,
                          final ProductController productController) {
        Objects.requireNonNull(template, "template must not be null.");
        Objects.requireNonNull(jwtTokenUtil, "jwtTokenUtil must not be null.");
        Objects.requireNonNull(bidService, "bidService must not be null.");
        Objects.requireNonNull(productController, "productController must not be null.");
        this.jwtTokenUtil = jwtTokenUtil;
        this.bidService = bidService;
        this.template = template;
        this.productController = productController;
    }

    @PostMapping("/bid/newBid")
    public ResponseEntity<Bid> saveBidFromUser(@RequestBody final Bid bid, final HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        if (!requestTokenHeader.equals("")) {
            jwtToken = requestTokenHeader.substring(7);
            if (!jwtTokenUtil.getUsernameFromToken(jwtToken).equals(bid.getUser().getEmail())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        final Bid savedBid = bidService.saveBidFromUser(bid.getProduct().getId(), bid.getUser().getEmail(), bid.getValue());
        if (savedBid == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.highestBid = savedBid;
        return new ResponseEntity<>(savedBid, HttpStatus.OK);
    }

    @MessageMapping("/send/message/highestBid")
    @SendToUser
    public void onHighestBidNotify(final UserWatchProductId userProduct) {
        this.productController.usersWatchingProduct.forEach(uw -> {
            if (uw.getProductId().equals(userProduct.getProductId()) && !uw.getSessionId().equals(userProduct.getSessionId())) {
                SimpMessageHeaderAccessor headerAcc = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                headerAcc.setSessionId(uw.getSessionId());
                headerAcc.setLeaveMutable(true);
                BidInfo bidInfo = new BidInfo((long) 0, (long) 0, bidService.numberOfBidsByProduct(userProduct.getProductId()),
                        Collections.emptyList(), highestBid);
                this.template.convertAndSendToUser(uw.getSessionId(), "/queue/highestBid", bidInfo, headerAcc.getMessageHeaders());
            }
        });

    }
}

