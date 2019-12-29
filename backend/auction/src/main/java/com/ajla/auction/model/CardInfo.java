package com.ajla.auction.model;

public class CardInfo {
    private String number;
    private Long exp_month;
    private Long exp_year;
    private String cvc;
    private String name;

    private String emailUser;
    private String customerId;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getExp_month() {
        return exp_month;
    }

    public void setExp_month(Long exp_month) {
        this.exp_month = exp_month;
    }

    public Long getExp_year() {
        return exp_year;
    }

    public void setExp_year(Long exp_year) {
        this.exp_year = exp_year;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
