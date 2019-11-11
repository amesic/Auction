package com.ajla.auction.repo;

import com.ajla.auction.model.Bid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {
    //my methods
    Bid findBidById(Long id);
    List<Bid> findByProductIdOrderByDateAsc(Long id);
}
