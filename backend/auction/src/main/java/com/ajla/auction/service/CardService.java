package com.ajla.auction.service;

import com.ajla.auction.model.Card;
import com.ajla.auction.repo.CardRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CardService implements ICardService{
    private final CardRepository cardRepository;

    public CardService(final CardRepository cardRepository) {
        Objects.requireNonNull(cardRepository, "cardRepository must not be null.");
        this.cardRepository = cardRepository;
    }


    @Override
    public Card saveCustomerId(final String customerId) {
        Card card = new Card();
        card.setCustomerId(customerId);
        cardRepository.save(card);
        return cardRepository.findByCustomerId(customerId);
    }


}
