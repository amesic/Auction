package com.ajla.auction.controller;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"})
@RestController
@RequestMapping("/bid")
public class BidController {
    final BidService bidService;

    @Autowired
    public BidController(final BidService bidService) {
        Objects.requireNonNull(bidService, "bidService must not be null.");
        this.bidService = bidService;
    }

    @GetMapping("/bidsOfProduct")
    public ResponseEntity<List<Bid>> getBidsOfProduct (@RequestParam("id") final Long id) {
        return new ResponseEntity<>(bidService.bidsOfProduct(id), HttpStatus.OK);
    }
    @PostMapping("/newBid")
    public ResponseEntity<Bid> saveBidFromUser(@RequestBody final Bid bid) {
        final Bid savedBid = bidService.saveBidFromUser(bid.getProduct().getId(), bid.getUser().getEmail(), bid.getValue());
        if (savedBid == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedBid, HttpStatus.OK);
    }
}
