package com.ajla.auction.service;

import com.ajla.auction.model.CardInfo;

public interface IStripeService {
    CardInfo createCustomer(final CardInfo cardInfo) throws Exception;
    CardInfo updateCustomer(final String customerId, final CardInfo cardInfo) throws Exception;
}
