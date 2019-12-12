package com.ajla.auction.model;

import java.time.LocalDate;
import java.util.List;

public class ProductInfoBid extends Product {
    private Long highestBid;
    private Long numberOfBids;

    public Long getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Long highestBid) {
        this.highestBid = highestBid;
    }

    public Long getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(Long numberOfBids) {
        this.numberOfBids = numberOfBids;
    }
}
