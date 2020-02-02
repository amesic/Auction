package com.ajla.auction.model;

import java.time.LocalDate;
import java.util.List;

public class ProductInfoBid extends Product {
    private Double highestBid;
    private Long numberOfBids;

    public Double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Double highestBid) {
        this.highestBid = highestBid;
    }

    public Long getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(Long numberOfBids) {
        this.numberOfBids = numberOfBids;
    }
}
