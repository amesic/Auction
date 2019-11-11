package com.ajla.auction.controller;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;
import com.ajla.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/product")
public class ProductController {
    //properties
    final ProductService productService;

    //dependency injection
    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/featureProduct")
    public ResponseEntity<List<Product>> getFeatureProducts() {
        return  productService.getFeatureProducts();
    }
    @GetMapping("/featureCollection")
    public ResponseEntity<List<Product>> getFeatureCollection() {
        return  productService.getFeatureCollection();
    }
    @GetMapping("/advertisement")
    public ResponseEntity<Product> getAdvertisementProduct() {
        return  productService.getAdvertisementProduct();
    }
    //http://localhost:8080/product/newArrivals?page=0&size=5
    @GetMapping("/newArrivals")
    public ResponseEntity<PaginationInfo<Product>> findPaginatedNewArrivals(@RequestParam("page") int page, @RequestParam("size") int size) {
       return productService.findPaginatedNewArrivals(page, size);
    }
    @GetMapping("/lastChance")
    public ResponseEntity<PaginationInfo<Product>> findPaginatedLastChance(@RequestParam("page") int page, @RequestParam("size") int size) {
        return productService.findPaginatedLastChance(page, size);
    }
    @GetMapping("/singleProduct")
    public ResponseEntity<Product> findSingleProduct (@RequestParam("id") long id) {
        return productService.findSingleProduct(id);
    }
    @GetMapping("/relatedProducts")
    public ResponseEntity<List<Product>> getRelatedProducts (@RequestParam("idProduct") long idProduct) {
        return productService.getRelatedProducts(idProduct);
    }
}
