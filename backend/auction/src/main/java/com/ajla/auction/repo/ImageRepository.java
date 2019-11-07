package com.ajla.auction.repo;

import com.ajla.auction.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    //my methods
    //ImageProduct findImageProductById(Long id);
    //List<ImageProduct> findImageProductByProduct(Product product);
    Image findImageProductById (Long id);
}
