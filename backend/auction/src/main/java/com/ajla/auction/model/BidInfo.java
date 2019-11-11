package com.ajla.auction.model;

import java.util.List;

public class BidInfo {
    private List<Bid> bidsOfProduct;

    private Bid highestBid;
    private Long numberOfBids;
    private int days;
    private int months;
    private int years;

    public List<Bid> getBidsOfProduct() {
        return bidsOfProduct;
    }

    public void setBidsOfProduct(List<Bid> bidsOfProduct) {
        this.bidsOfProduct = bidsOfProduct;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
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
