package com.ajla.auction.service;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.ProductInfoBid;
import com.ajla.auction.model.Watchlist;

public interface IWatchlistService {
    Watchlist saveNewProductInfoWatchlistOfUser(final String email, final Long idProduct);
    PaginationInfo<ProductInfoBid> findWatchlistByUser(final String email, final Long pageNumber, final Long size);
    PaginationInfo<ProductInfoBid> deleteItemFromWatchlist(final String email,
                                                           final Long idProduct,
                                                           final Long pageNumber,
                                                           final Long size);
}
