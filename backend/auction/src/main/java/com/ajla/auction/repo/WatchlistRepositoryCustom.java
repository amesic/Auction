package com.ajla.auction.repo;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.ProductInfoBid;
import com.ajla.auction.model.Watchlist;

public interface WatchlistRepositoryCustom {
    PaginationInfo<ProductInfoBid> findWatchlistByUser(final Long idUser, final Long pageNumber, final Long size);

    Watchlist getWatchlistByProductIdAndUserId(final Long idUser, final Long idProduct);

    PaginationInfo<ProductInfoBid> deleteItemFromWatchlist(final Long idUser,
                                                           final Long idProduct,
                                                           final Long pageNumber,
                                                           final Long size);
}
