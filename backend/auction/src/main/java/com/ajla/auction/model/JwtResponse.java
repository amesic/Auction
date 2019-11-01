package com.ajla.auction.model;

import java.io.Serializable;
//This class is required for creating a response containing the JWT to be returned to the user.
public class JwtResponse implements Serializable {
    private final String jwtToken;
    private final String username;

    public JwtResponse(String username, String jwtToken) {
        this.jwtToken = jwtToken;
        this.username = username;
    }

    public String getToken() {
        return this.jwtToken;
    }
    public String getUsername() {
        return this.username;
    }
}
