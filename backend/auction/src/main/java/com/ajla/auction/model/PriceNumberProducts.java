package com.ajla.auction.model;

public class PriceNumberProducts {
    Double name;
    Long value;

    public PriceNumberProducts(Double name, Long value) {
        this.name = name;
        this.value = value;
    }

    public Double getName() {
        return name;
    }

    public void setName(Double name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
