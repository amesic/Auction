package com.ajla.auction.service;

import com.ajla.auction.model.CardInfo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.PaymentSourceCollection;
import com.stripe.model.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService implements IStripeService{

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    @Override
    public CardInfo createCustomer(final CardInfo cardInfo) throws StripeException {
        Map<String, Object> card = new HashMap<>();
        card.put("number", cardInfo.getNumber());
        card.put("exp_month", cardInfo.getExp_month());
        card.put("exp_year", cardInfo.getExp_year());
        card.put("cvc", cardInfo.getCvc());
        card.put("name", cardInfo.getName());
        Map<String, Object> params = new HashMap<>();
        params.put("card", card);
        Token token = Token.create(params);

        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", cardInfo.getEmailUser());
        customerParams.put("source", token.getId());
        //create a new customer
        Customer customer = Customer.create(customerParams);
        CardInfo cardInfoSaved = new CardInfo();
        cardInfoSaved.setExp_month(token.getCard().getExpMonth());
        cardInfoSaved.setExp_year(token.getCard().getExpYear());
        cardInfoSaved.setName(token.getCard().getName());
        cardInfoSaved.setNumber(token.getCard().getLast4());
        cardInfoSaved.setCustomerId(customer.getId());
        return cardInfoSaved;
    }
    @Override
    public CardInfo updateCustomer(final String customerId, final CardInfo cardInfo) throws StripeException {
        Stripe.apiKey = API_SECRET_KEY;
            Customer customer1 = Customer.retrieve(customerId);
            Card card = (Card) customer1.getSources().getData().get(0);
            Map<String, Object> params = new HashMap<>();
            params.put("name", cardInfo.getName());
            params.put("exp_month", cardInfo.getExp_month());
            params.put("exp_year", cardInfo.getExp_year());
            Card updateCard = card.update(params);

        CardInfo cardInfoSaved = new CardInfo();
        cardInfoSaved.setExp_month(updateCard.getExpMonth());
        cardInfoSaved.setExp_year(updateCard.getExpYear());
        cardInfoSaved.setName(updateCard.getName());
        cardInfoSaved.setNumber(updateCard.getLast4());
        return cardInfoSaved;
    }

    @Override
    public CardInfo getUserCardDetails(final String customerId) throws StripeException {
        Stripe.apiKey = API_SECRET_KEY;
        Customer customer1 = Customer.retrieve(customerId);
        Card card = (Card) customer1.getSources().getData().get(0);

        CardInfo cardInfo = new CardInfo();
        cardInfo.setExp_month(card.getExpMonth());
        cardInfo.setExp_year(card.getExpYear());
        cardInfo.setName(card.getName());
        cardInfo.setNumber(card.getLast4());
        cardInfo.setBrand(card.getBrand());
        return cardInfo;

    }
}
