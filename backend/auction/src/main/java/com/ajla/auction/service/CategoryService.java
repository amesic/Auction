package com.ajla.auction.service;

import com.ajla.auction.model.Category;
import com.ajla.auction.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{
    //properties
    private final CategoryRepository categoryRepo;

    //dependency injection
    @Autowired
    public CategoryService(final CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories(Long numberOfCategories) {
        final List<Category> listCategories;
        if(numberOfCategories == null) {
           listCategories = categoryRepo.findAll();
        }
        else {
            listCategories = categoryRepo.findExactNumberOfCategories(numberOfCategories);
        }
        return new ResponseEntity<List<Category>>(listCategories, HttpStatus.OK);
    }

}
