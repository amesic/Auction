package com.ajla.auction.model;

import java.util.List;
import java.util.Map;

public class PriceProductInfo {
    private Double avgPrice;
    private List<Object> priceNumber;

    public Double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public List<Object> getPriceNumber() {
        return priceNumber;
    }

    public void setPriceNumber(List<Object> priceNumber) {
        this.priceNumber = priceNumber;
    }
}
