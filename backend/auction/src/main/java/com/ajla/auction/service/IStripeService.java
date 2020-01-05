package com.ajla.auction.service;

import com.ajla.auction.model.CardInfo;
import com.ajla.auction.model.User;
import com.stripe.exception.StripeException;

import java.io.IOException;

public interface IStripeService {
    CardInfo createCustomer(final CardInfo cardInfo) throws Exception;

    CardInfo updateCustomer(final String customerId, final CardInfo cardInfo) throws Exception;

    CardInfo getUserCardDetails(final String customerId) throws StripeException;

    String createCharge(final String customerId,
                        final String accountId,
                        final String productName,
                        final int amount) throws StripeException;

    String createStripeAccountForSeller(final User seller, final CardInfo cardInfo) throws StripeException;
}
