package com.ajla.auction.model;

public class NewBid {
    private Long idProduct;
    private String emailUser;
    private Long value;
    private Long highestValue;

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getHighestValue() {
        return highestValue;
    }

    public void setHighestValue(Long highestValue) {
        this.highestValue = highestValue;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}
