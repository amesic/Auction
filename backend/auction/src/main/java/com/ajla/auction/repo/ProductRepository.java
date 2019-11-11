package com.ajla.auction.repo;

import com.ajla.auction.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Product findProductById(Long id);
}
