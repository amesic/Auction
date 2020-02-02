package com.ajla.auction.model;

import java.util.List;

public class PriceProductInfo {
    private Double avgPrice;
    private List<PriceNumberProducts> priceNumber;

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public List<PriceNumberProducts> getPriceNumber() {
        return priceNumber;
    }

    public void setPriceNumber(List<PriceNumberProducts> priceNumber) {
        this.priceNumber = priceNumber;
    }
}
