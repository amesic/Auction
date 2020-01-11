package com.ajla.auction.service;

import com.ajla.auction.model.Address;
import com.ajla.auction.model.Card;
import com.ajla.auction.model.RequiredInfoUser;
import com.ajla.auction.model.User;

public interface IUserService {
    User findByEmail(final String email);

    Boolean saveDataFromUser(final User user);

    void saveCardId(final Card card, final String email);

    User saveUserRequiredInfo(final RequiredInfoUser requiredInfoUser) throws Throwable;

    Address saveAddressOfUser(final User user);
}
