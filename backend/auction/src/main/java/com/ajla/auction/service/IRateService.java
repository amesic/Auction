package com.ajla.auction.service;

import com.ajla.auction.model.Rate;

public interface IRateService {
    Double getRatingOfSeller(final String emailSeller);
    Rate saveRateFromUser(final Rate rate);
}
