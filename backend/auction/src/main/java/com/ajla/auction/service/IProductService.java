package com.ajla.auction.service;

import com.ajla.auction.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {
    ResponseEntity<List<Product>> getAllProducts();
    ResponseEntity<Product> getAdvertisementProduct();
    ResponseEntity<List<Product>> getFeatureProducts();
    ResponseEntity<List<Product>> getFeatureCollection();
    ResponseEntity<List<Product>>  findPaginatedNewArrivals(int page, int size);
    ResponseEntity<List<Product>>  findPaginatedLastChance(int page, int size);

}
