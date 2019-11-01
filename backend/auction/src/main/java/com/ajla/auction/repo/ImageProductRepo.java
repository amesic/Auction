package com.ajla.auction.repo;

import com.ajla.auction.model.ImageProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepo extends CrudRepository<ImageProduct, Long> {
    //my methods
}
