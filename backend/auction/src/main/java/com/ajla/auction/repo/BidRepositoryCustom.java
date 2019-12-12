package com.ajla.auction.repo;


import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.model.Product;

import java.util.List;

public interface BidRepositoryCustom {
    Boolean checkIfThereIsGreaterValue(final Long valueFromUser, final Product product);
    BidInfo getBidsOfPage(final Long pageNumber, final Long size, final Long idProduct);
    Long numberOfBidsByProduct(final Long productId);
    List<Bid> bidsByProduct(final Long productId);
}
