package com.ajla.auction.repo;

import com.ajla.auction.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long>, RateRepositoryCustom {
    Rate findRateById(Long id);
    List<Rate> findRateBySellerId(Long id);
}
