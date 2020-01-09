package com.ajla.auction.service;

import com.ajla.auction.model.CardInfo;
import com.ajla.auction.model.User;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.Charge;
import com.stripe.model.File;
import com.stripe.model.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeService implements IStripeService{

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    @Override
    public CardInfo createCustomer(final CardInfo cardInfo) throws StripeException {
        Stripe.apiKey = API_SECRET_KEY;

        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", cardInfo.getEmailUser());
        customerParams.put("source", cardInfo.getToken());

        //create a new customer
        Customer savedCustomer = Customer.create(customerParams);
        Card card = (Card) savedCustomer.getSources().getData().get(0);

        CardInfo cardInfoSaved = new CardInfo();
        cardInfoSaved.setExp_month(card.getExpMonth());
        cardInfoSaved.setExp_year(card.getExpYear());
        cardInfoSaved.setName(card.getName());
        cardInfoSaved.setNumber(card.getLast4());
        cardInfoSaved.setCustomerId(savedCustomer.getId());
        cardInfoSaved.setBrand(card.getBrand());
        return cardInfoSaved;
    }

    @Override
    public CardInfo updateCustomer(final String customerId, final CardInfo cardInfo) throws StripeException {
        Stripe.apiKey = API_SECRET_KEY;
        Customer customer = Customer.retrieve(customerId);
        Card card = (Card) customer.getSources().getData().get(0);
        card.delete();
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("source", cardInfo.getToken());
        Customer updatedCustomer = customer.update(customerParams);

        Card cardSaved = (Card) updatedCustomer.getSources().getData().get(0);

        CardInfo cardInfoSaved = new CardInfo();
        cardInfoSaved.setExp_month(cardSaved.getExpMonth());
        cardInfoSaved.setExp_year(cardSaved.getExpYear());
        cardInfoSaved.setName(cardSaved.getName());
        cardInfoSaved.setNumber(cardSaved.getLast4());
        cardInfoSaved.setBrand(cardSaved.getBrand());
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

    @Override
    public String createCharge(final String customerId,
                               final String accountId,
                               final String productName,
                               final int amount) throws StripeException {
        Stripe.apiKey = API_SECRET_KEY;
        Customer customer1 = Customer.retrieve(customerId);
        Card card = (Card) customer1.getSources().getData().get(0);

        Map<String, Object> transferData = new HashMap<>();
        transferData.put("destination", accountId);

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "usd");
        chargeParams.put("description", "Payment for " + productName);
        chargeParams.put("customer", customerId);
        chargeParams.put("card", card.getId());
        chargeParams.put("transfer_data", transferData);

        Charge charge = Charge.create(chargeParams);
        return charge.getId();
    }

    @Override
    public String createStripeAccountForSeller(final User seller, final CardInfo cardInfo) throws StripeException {
        Stripe.apiKey = API_SECRET_KEY;
        List<Object> requestedCapabilities = new ArrayList<>();
        requestedCapabilities.add("card_payments");
        requestedCapabilities.add("transfers");

        //hardcoded, needs to be info from user
        Map<String, Object> address = new HashMap<>();
        address.put("city", "New York");
        address.put("postal_code", "11216");
        address.put("country", "US");
        address.put("state", "New York");
        address.put("line1", "707 Nostrand Ave, Brooklyn, NY 11216, United States");

        Map<String, Object> dob = new HashMap<>();
        dob.put("day", 12);
        dob.put("month", 12);
        dob.put("year", 1990);

        Map<String, Object> fileParamsFront = new HashMap<>();
        fileParamsFront.put("purpose", "identity_document");
        fileParamsFront.put("file", new java.io.File("C:\\Users\\G3 16GB\\Desktop\\Auction\\backend\\imagesForStripe\\img1.jpg"));
        String frontId = File.create(fileParamsFront).getId();


        Map<String, Object> fileParamsBack = new HashMap<>();
        fileParamsBack.put("purpose", "identity_document");
        fileParamsBack.put("file", new java.io.File("C:\\Users\\G3 16GB\\Desktop\\Auction\\backend\\imagesForStripe\\img2.jpg"));
        String backId = File.create(fileParamsBack).getId();

        Map<String, Object> document= new HashMap<>();
        document.put("front", frontId);
        document.put("back", backId);

        Map<String, Object> verification= new HashMap<>();
        verification.put("document", document);

        Map<String, Object> individual = new HashMap<>();
        individual.put("address", address);
        individual.put("dob", dob);
        individual.put("first_name", "Something");
        individual.put("last_name", "Something");
        individual.put("ssn_last_4", "8888");
        individual.put("email", seller.getEmail());
        individual.put("phone", "+1 (718) 493-1375");
        individual.put("id_number", "000008888");
        individual.put("verification", verification);

        Map<String, Object> business_profile = new HashMap<>();
        business_profile.put("name", "business of " + seller.getUserName());
        business_profile.put("url", "https://atlantbh-auction.herokuapp.com");
        business_profile.put("product_description", "General Contractors-Residential and Commercial");
        business_profile.put("mcc", "5734");

        Map<String, Object> tos_acceptance= new HashMap<>();
        tos_acceptance.put("date",(long) System.currentTimeMillis() / 1000L);
        tos_acceptance.put("ip", "8.8.8.8");


        Map<String, Object> params = new HashMap<>();
        params.put("type", "custom");
        params.put("country", "US");
        params.put("email", seller.getEmail());
        params.put("business_type", "individual");
        //params.put("external_account", token.getId());
        params.put("individual", individual);
        params.put("business_profile", business_profile);
        params.put("tos_acceptance", tos_acceptance);
        params.put("requested_capabilities", requestedCapabilities);

        Account account = Account.create(params);

        return account.getId();
    }
}
