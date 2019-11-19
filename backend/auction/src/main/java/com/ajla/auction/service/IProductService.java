package com.ajla.auction.service;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {
    Product getAdvertisementProduct();
    List<Product> getFeatureProducts();
    List<Product> getFeatureCollection();
    PaginationInfo<Product> findPaginatedNewArrivals(final int page, final int size);
    PaginationInfo<Product> findPaginatedLastChance(final int page, final int size);
    Product findSingleProduct(final Long id);
    List<Product> getRelatedProducts(final Long idProduct);
    Boolean userIsSellerOfProduct(final Long idUser, final Long idProduct);
}
