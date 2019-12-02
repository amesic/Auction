package com.ajla.auction.service;

import com.ajla.auction.model.Characteristic;
import com.ajla.auction.model.NumberOfProductsInfo;
import com.ajla.auction.repo.CharacteristicRepository;
import com.ajla.auction.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CharacteristicService implements ICharacteristicService{
    private final CharacteristicRepository characteristicRepository;
    private final ProductRepository productRepository;

    public CharacteristicService(final CharacteristicRepository characteristicRepository,
                                 final ProductRepository productRepository) {
        Objects.requireNonNull(characteristicRepository, "characteristicRepository must not be null.");
        Objects.requireNonNull(productRepository, "productRepository must not be null.");
        this.characteristicRepository = characteristicRepository;
        this.productRepository = productRepository;
    }


    @Override
    public List<Characteristic> listOfAllCharacteristic() {
        return characteristicRepository.findAll();
    }
    @Override
    public NumberOfProductsInfo characteristic(final String name,
                                               final Long subcategoryId,
                                               final List<Long> listOfCharacteristicClicked,
                                               final String searchUser,
                                               final Double lowerBound,
                                               final Double upperBound) {
        return productRepository.numberOfProductsByCharacteristic(characteristicRepository.findCharacteristicByName(name), subcategoryId, listOfCharacteristicClicked, searchUser, lowerBound, upperBound);
    }
}
