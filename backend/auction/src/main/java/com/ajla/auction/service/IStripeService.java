package com.ajla.auction.service;

import com.ajla.auction.model.CardInfo;
import com.stripe.exception.StripeException;

public interface IStripeService {
    CardInfo createCustomer(final CardInfo cardInfo) throws Exception;
    CardInfo updateCustomer(final String customerId, final CardInfo cardInfo) throws Exception;
    CardInfo getUserCardDetails(final String customerId) throws StripeException;
}
