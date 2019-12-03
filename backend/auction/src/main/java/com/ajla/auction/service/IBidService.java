package com.ajla.auction.service;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;

import java.util.List;


public interface IBidService {
    BidInfo bidsOfProduct(final Long pageNumber, final Long size, final Long id);
    Bid saveBidFromUser(final Long idProduct, final String emailUser, final Long value);
}
