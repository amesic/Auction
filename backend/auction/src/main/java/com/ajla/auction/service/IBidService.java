package com.ajla.auction.service;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface IBidService {
    ResponseEntity<BidInfo> bidInfo(Long id);
    ResponseEntity<Bid> saveBidFromUser(Long idProduct, String emailUser, Long value, Long highestValue);
    ResponseEntity<Bid> findBidFromUser(String emailUser, Long idProduct);
}
