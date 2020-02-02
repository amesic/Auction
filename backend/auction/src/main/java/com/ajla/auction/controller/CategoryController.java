package com.ajla.auction.controller;

import com.ajla.auction.model.Category;
import com.ajla.auction.model.NumberOfProductsInfo;
import com.ajla.auction.service.CategoryService;
import com.ajla.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CategoryController(final CategoryService categoryService,
                              final ProductService productService) {
        Objects.requireNonNull(categoryService, "categoryService must not be null.");
        Objects.requireNonNull(productService, "productService must not be null.");
        this.productService = productService;
        this.categoryService = categoryService;
    }

    //http://localhost:8080/category/all?numberOfCategories=9 or
    //http://localhost:8080/category/all
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam(required = false) final Long numberOfCategories) {
        return new ResponseEntity<>(categoryService.getAllCategories(numberOfCategories), HttpStatus.OK);
    }

    @GetMapping("/numberOfProductsBySubcategory")
    public ResponseEntity<List<NumberOfProductsInfo>> getNumberOfProducts() {
        return new ResponseEntity<>(productService.numberOfProductsBySubcategories(), HttpStatus.OK);
    }
}
