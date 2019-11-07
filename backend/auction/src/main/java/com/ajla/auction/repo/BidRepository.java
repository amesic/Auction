package com.ajla.auction.repo;

import com.ajla.auction.model.Bid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {
    //my methods
}
