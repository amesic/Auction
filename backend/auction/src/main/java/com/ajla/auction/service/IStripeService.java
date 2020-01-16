package com.ajla.auction.service;

import com.ajla.auction.model.CardInfo;
import com.ajla.auction.model.User;
import com.stripe.exception.StripeException;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IStripeService {
    CardInfo createCustomer(final CardInfo cardInfo) throws Exception;

    CardInfo updateCustomer(final String customerId, final CardInfo cardInfo) throws Exception;

    CardInfo getUserCardDetails(final String customerId) throws StripeException;

    String createCharge(final User customer,
                        final Long productId,
                        final String accountId,
                        final String productName,
                        final String token,
                        final int amount) throws StripeException;

    String createStripeAccountForSeller(final User seller, final CardInfo cardInfo) throws StripeException, IOException;

    Boolean checkIfCustomerPaidItem(final User customer, final String productName) throws StripeException;
}
