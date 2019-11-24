package com.ajla.auction.service;

import com.ajla.auction.model.Characteristic;
import com.ajla.auction.model.NumberOfProductsInfo;

import java.util.List;

public interface ICharacteristicService {
    List<Characteristic> listOfAllCharacteristic();
    NumberOfProductsInfo characteristic(String name);
}