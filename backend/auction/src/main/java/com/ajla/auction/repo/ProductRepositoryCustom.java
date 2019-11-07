package com.ajla.auction.repo;

import com.ajla.auction.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getAllFeatureProducts();
    List<Product> getAllFeatureCollection();
    List<Product> getAllLastChanceProducts(int page, int size);
    List<Product> getAllNewArrivalProducts(int page, int size);
}
