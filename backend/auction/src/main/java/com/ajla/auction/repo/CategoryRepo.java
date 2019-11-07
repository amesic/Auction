package com.ajla.auction.repo;

import com.ajla.auction.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends CrudRepository<Category, Long> {
    //my methods
}
