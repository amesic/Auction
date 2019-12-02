package com.ajla.auction.service;

import com.ajla.auction.model.Characteristic;
import com.ajla.auction.model.NumberOfProductsInfo;

import java.util.List;

public interface ICharacteristicService {
    List<Characteristic> listOfAllCharacteristic();
    NumberOfProductsInfo characteristic(final String name,
                                        final Long subcategoryId,
                                        final List<Long> listOfCharacteristicClicked,
                                        final String searchUser,
                                        final Double lowerBound,
                                        final Double upperBound);
}
