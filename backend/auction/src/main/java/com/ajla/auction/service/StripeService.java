package com.ajla.auction.service;

import com.ajla.auction.repo.CardRepository;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class StripeService implements IStripeService{

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;


    @Override
    public String createCustomer(String email) {
        String id = null;
        try {
            Stripe.apiKey = API_SECRET_KEY;
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("email", email);
            //customerParams.put("source", tokenCard);
            //create a new customer
            Customer customer = Customer.create(customerParams);
            id = customer.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }
}
