package com.ajla.auction.service;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.UserProductInfoBid;

import java.util.List;


public interface IBidService {
    PaginationInfo<Bid> bidsOfProduct(final Long pageNumber, final Long size, final Long id);

    Bid saveBidFromUser(final Long idProduct, final String emailUser, final Double value);

    PaginationInfo<UserProductInfoBid> bidsOfUser(final Long pageNumber, final Long size, final String email);
}
