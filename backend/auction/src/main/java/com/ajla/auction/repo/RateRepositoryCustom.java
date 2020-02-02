package com.ajla.auction.repo;

import com.ajla.auction.model.User;

public interface RateRepositoryCustom {
    Double ratingOfSeller(User seller);
}
