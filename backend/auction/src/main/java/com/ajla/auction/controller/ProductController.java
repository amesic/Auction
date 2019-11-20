package com.ajla.auction.controller;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;
import com.ajla.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        Objects.requireNonNull(productService, "productService must not be null.");
        this.productService = productService;
    }

    @GetMapping("/featureProduct")
    public ResponseEntity<List<Product>> getFeatureProducts() {
        return new ResponseEntity<>(productService.getFeatureProducts(), HttpStatus.OK);
    }
    @GetMapping("/featureCollection")
    public ResponseEntity<List<Product>> getFeatureCollection() {
        return new ResponseEntity<>(productService.getFeatureCollection(), HttpStatus.OK);
    }
    @GetMapping("/advertisement")
    public ResponseEntity<Product> getAdvertisementProduct() {
        return new ResponseEntity<>(productService.getAdvertisementProduct(), HttpStatus.OK);
    }
    //http://localhost:8080/product/newArrivals?page=0&size=5
    @GetMapping("/newArrivals")
    public ResponseEntity<PaginationInfo<Product>> findPaginatedNewArrivals(@RequestParam("page") final Long page, @RequestParam("size") final Long size) {
       return new ResponseEntity<>(productService.findPaginatedNewArrivals(page, size), HttpStatus.OK);
    }
    @GetMapping("/lastChance")
    public ResponseEntity<PaginationInfo<Product>> findPaginatedLastChance(@RequestParam("page") final Long page, @RequestParam("size") final Long size) {
        return new ResponseEntity<>(productService.findPaginatedLastChance(page, size), HttpStatus.OK);
    }
    @GetMapping("/singleProduct")
    public ResponseEntity<Product> findSingleProduct (@RequestParam("id") final Long id) {
        return new ResponseEntity<>(productService.findSingleProduct(id), HttpStatus.OK);
    }
    @GetMapping("/relatedProducts")
    public ResponseEntity<List<Product>> getRelatedProducts (@RequestParam("id") final Long idProduct) {
        return new ResponseEntity<>(productService.getRelatedProducts(idProduct), HttpStatus.OK);
    }
}
