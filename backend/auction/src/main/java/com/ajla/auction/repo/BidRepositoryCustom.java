package com.ajla.auction.repo;


import com.ajla.auction.model.BidInfo;
import com.ajla.auction.model.Product;

public interface BidRepositoryCustom {
    Boolean checkIfThereIsGreaterValue(final Long valueFromUser, final Product product);
    BidInfo getBidsOfPage(final Long pageNumber, final Long size, final Long idProduct);
}
