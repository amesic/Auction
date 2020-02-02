package com.ajla.auction.model;

public class RequiredInfoUser extends User {
    private String emailLoggedUser;

    private String token;

    public String getEmailLoggedUser() {
        return emailLoggedUser;
    }

    public void setEmailLoggedUser(String emailLoggedUser) {
        this.emailLoggedUser = emailLoggedUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
