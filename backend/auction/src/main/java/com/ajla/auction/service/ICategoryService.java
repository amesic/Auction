package com.ajla.auction.service;

import com.ajla.auction.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories(final Long numberOfCategories);
}
