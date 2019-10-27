package com.ajla.auction.service;

import com.ajla.auction.model.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    public Long findByEmailPassword(String email, String password);
    public Long findByEmail(String email);
    public ResponseEntity<String> saveDataFromUser(User user);
}
