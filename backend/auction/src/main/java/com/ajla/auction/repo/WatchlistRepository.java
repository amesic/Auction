package com.ajla.auction.repo;

import com.ajla.auction.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long>, WatchlistRepositoryCustom {
}

