package com.ajla.auction.model;

public class ProductViewers {
    private Long productId;
    private Long numberOfCurrentViewers = (long) 0;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getNumberOfCurrentViewers() {
        return numberOfCurrentViewers;
    }

    public void setNumberOfCurrentViewers(Long numberOfCurrentViewers) {
        this.numberOfCurrentViewers = numberOfCurrentViewers;
    }
    public void increment() {
        this.numberOfCurrentViewers++;
    }
    public void decrement() {
        this.numberOfCurrentViewers--;
    }
}
