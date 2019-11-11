package com.ajla.auction.controller;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.model.NewBid;
import com.ajla.auction.repo.BidRepository;
import com.ajla.auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/bid")
public class BidController {
    //properties
    final BidService bidService;

    //dependency injection
    @Autowired
    public BidController(final BidRepository bidRepository, BidService bidService) {
        this.bidService = bidService;
    }
    @GetMapping("/bidsOfProduct")
    public ResponseEntity<BidInfo> getBidsInfoOfProduct (@RequestParam("id") long id) {
        return bidService.bidInfo(id);
    }
    @PostMapping("/newBid")
    public ResponseEntity<Bid> saveBidFromUser(@RequestBody NewBid newBid) {
        return  bidService.saveBidFromUser(newBid.getIdProduct(), newBid.getEmailUser(), newBid.getValue(), newBid.getHighestValue());
    }


}
