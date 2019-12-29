package com.ajla.auction.service;

import com.ajla.auction.model.Card;

public interface ICardService {
    Card saveCustomerId(final String customerId);
    String checkForCustomerId(Long cardId);
}
