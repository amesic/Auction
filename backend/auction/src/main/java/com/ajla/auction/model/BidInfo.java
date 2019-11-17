package com.ajla.auction.model;

import java.util.List;

public class BidInfo {

    private List<Bid> bidsOfProduct;
    private Bid highestBid;
    private Long numberOfBids;

    public List<Bid> getBidsOfProduct() {
        return bidsOfProduct;
    }

    public void setBidsOfProduct(List<Bid> bidsOfProduct) {
        this.bidsOfProduct = bidsOfProduct;
    }


    public Bid getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Bid highestBid) {
        this.highestBid = highestBid;
    }

    public Long getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(Long numberOfBids) {
        this.numberOfBids = numberOfBids;
    }
}
