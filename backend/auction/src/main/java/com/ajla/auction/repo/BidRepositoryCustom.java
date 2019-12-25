package com.ajla.auction.repo;


import com.ajla.auction.model.Bid;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;
import com.ajla.auction.model.UserProductInfoBid;
import com.ajla.auction.model.User;

import java.util.List;

public interface BidRepositoryCustom {
    Boolean checkIfThereIsGreaterValue(final Double valueFromUser, final Product product);

    PaginationInfo<Bid> getProductBids(final Long pageNumber,
                                       final Long size,
                                       final Long idProduct);

    Long numberOfBidsForProduct(final Long productId);

    List<Bid> bidsByProduct(final Long productId);

    PaginationInfo<UserProductInfoBid> getUserBids(final Long pageNumber, final Long size, final Long userId);

    Bid saveBidFromUser(final User user, final Product product, final Double value);
}
