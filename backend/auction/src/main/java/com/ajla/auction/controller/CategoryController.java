package com.ajla.auction.controller;

import com.ajla.auction.model.Category;
import com.ajla.auction.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"})
@RestController
@RequestMapping("/category")
public class CategoryController {
    //properties
    private final CategoryService categoryService;

    //dependency injection
    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //http://localhost:8080/category/all?numberOfCategories=9 or
    //http://localhost:8080/category/all
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam(required = false) Long numberOfCategories) {
        return categoryService.getAllCategories(numberOfCategories);
    }


}
