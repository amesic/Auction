package com.ajla.auction.service;

import com.ajla.auction.model.Bid;
import java.util.List;


public interface IBidService {
    List<Bid> bidsOfProduct(final Long id);
    Bid saveBidFromUser(final Long idProduct, final String emailUser, final Long value);
}
