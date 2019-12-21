package com.ajla.auction.repo;


import com.ajla.auction.model.Bid;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;

import java.util.List;

public interface BidRepositoryCustom {
    Boolean checkIfThereIsGreaterValue(final Long valueFromUser, final Product product);

    PaginationInfo<Bid> getProductBids(final Long pageNumber, final Long size, final Long idProduct);

    Long numberOfBidsForProduct(final Long productId);

    List<Bid> bidsByProduct(final Long productId);
}
