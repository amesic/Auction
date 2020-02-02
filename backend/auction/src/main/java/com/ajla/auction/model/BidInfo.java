package com.ajla.auction.model;

import java.util.List;

public class BidInfo extends PaginationInfo<Bid>{

    private Bid highestBid;

    public BidInfo(Long pageSize, Long pageNumber, Long totalNumberOfItems, List<Bid> items, Bid highestBid) {
        super(pageSize, pageNumber, totalNumberOfItems, items);
        this.highestBid = highestBid;
    }

    public Bid getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Bid highestBid) {
        this.highestBid = highestBid;
    }
}
