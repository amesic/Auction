package com.ajla.auction.repo;

import com.ajla.auction.model.Category;

import java.util.List;

//need this bc i want to write my own methods
public interface CategoryRepositoryCustom {
    List<Category> findExactNumberOfCategories(Long numberOfCategories);

}
