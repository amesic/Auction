package com.ajla.auction.repo;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getAllFeatureProducts();
    List<Product> getAllFeatureCollection();
    PaginationInfo<Product> getAllLastChanceProducts(int page, int size);
    PaginationInfo<Product> getAllNewArrivalProducts(int page, int size);
    List<Product> getRelatedProducts(Long idSubcategory, Long idProduct);
}
