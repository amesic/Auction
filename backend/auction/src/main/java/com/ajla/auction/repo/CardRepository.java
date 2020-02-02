package com.ajla.auction.repo;

import com.ajla.auction.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByCustomerId(String customerId);
    Card findCardById(Long id);
}
