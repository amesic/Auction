package com.ajla.auction.repo;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.ProductInfoBid;

public interface WatchlistRepositoryCustom {
    PaginationInfo<ProductInfoBid> findWatchlistByUser(final Long idUser, final Long pageNumber, final Long size);
}
