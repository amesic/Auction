package com.ajla.auction.repo;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getAllFeatureProducts();
    List<Product> getAllFeatureCollection();
    PaginationInfo<Product> getAllLastChanceProducts(final int page, final int size);
    PaginationInfo<Product> getAllNewArrivalProducts(final int page, final int size);
    Long getSubcategoryIdOfProduct (final Long idProduct);
    List<Product> getRelatedProducts(final Long idProduct);
}
