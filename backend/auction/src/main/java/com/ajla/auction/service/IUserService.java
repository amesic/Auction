package com.ajla.auction.service;

import com.ajla.auction.model.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    User findByEmail(final String email);

    Boolean saveDataFromUser(final User user);
}
