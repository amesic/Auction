package com.ajla.auction.service;

import com.ajla.auction.model.Category;
import com.ajla.auction.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(final CategoryRepository categoryRepository) {
        Objects.requireNonNull(categoryRepository, "categoryRepository must not be null.");
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories(final Long numberOfCategories) {
        final List<Category> listCategories;
        if(numberOfCategories == null) {
           listCategories = categoryRepository.findAll();
        } else {
            listCategories = categoryRepository.findExactNumberOfCategories(numberOfCategories);
        }
        return listCategories;
    }

}
