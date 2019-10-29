package com.ajla.auction.service;

import com.ajla.auction.model.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    public Long findByEmailPassword(final String email, final String password);
    public Long findByEmail(final String email);
    public ResponseEntity<String> saveDataFromUser(final User user);
}
