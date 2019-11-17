package com.ajla.auction.repo;


import com.ajla.auction.model.Product;

public interface BidRepositoryCustom {
    Boolean checkIfThereIsGreaterValue(final Long valueFromUser, final Product product);
}
