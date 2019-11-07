package com.ajla.auction.repo;

import com.ajla.auction.model.User;

//need this bc i want to write my own methods
public interface UserRepoMethods {
    User findByEmail(final String email);
}
