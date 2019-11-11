package com.ajla.auction.service;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;
import com.ajla.auction.repo.BidRepository;
import com.ajla.auction.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{
    //properties
    private final ProductRepository productRepo;
    private final BidRepository bidRepository;

    //dependency injection
    @Autowired
    public ProductService(final ProductRepository productRepo, final BidRepository bidRepository) {
        this.productRepo = productRepo;
        this.bidRepository = bidRepository;
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        final List<Product> listOfProducts = productRepo.findAll();
        return new ResponseEntity<List<Product>>(listOfProducts, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Product> getAdvertisementProduct() {
        return new ResponseEntity<> (productRepo.findProductById((long) 4), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<List<Product>> getFeatureProducts() {
        return new ResponseEntity<>(productRepo.getAllFeatureProducts(), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<List<Product>> getFeatureCollection() {
        return new ResponseEntity<>(productRepo.getAllFeatureCollection(), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<PaginationInfo<Product>> findPaginatedNewArrivals(int page, int size) {
        return new ResponseEntity<>(productRepo.getAllNewArrivalProducts(page, size), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<PaginationInfo<Product>> findPaginatedLastChance(int page, int size) {
        return new ResponseEntity<>(productRepo.getAllLastChanceProducts(page, size), HttpStatus.OK);
    }
    @Override
    public  ResponseEntity<Product> findSingleProduct (long id) {
        return new ResponseEntity<>(productRepo.findProductById(id), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<List<Product>> getRelatedProducts (Long idProduct) {
        return new ResponseEntity<>(productRepo.getRelatedProducts(idProduct), HttpStatus.OK);
    }
}
