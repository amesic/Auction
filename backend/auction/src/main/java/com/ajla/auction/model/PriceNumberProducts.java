package com.ajla.auction.model;

public class PriceNumberProducts {
    private Double price;
    private Long numberOfProductsByPrice;

    public PriceNumberProducts(Double price, Long numberOfProductsByPrice) {
        this.price = price;
        this.numberOfProductsByPrice = numberOfProductsByPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getNumberOfProductsByPrice() {
        return numberOfProductsByPrice;
    }

    public void setNumberOfProductsByPrice(Long numberOfProductsByPrice) {
        this.numberOfProductsByPrice = numberOfProductsByPrice;
    }
}
