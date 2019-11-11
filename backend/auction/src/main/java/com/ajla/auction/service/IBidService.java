package com.ajla.auction.service;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface IBidService {
    ResponseEntity<BidInfo> bidInfo(Long id);
    ResponseEntity<Bid> saveBidFromUser(Long idProduct, String emailUser, Long value, Long highestValue);


}
