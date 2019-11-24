package com.ajla.auction.controller;

import com.ajla.auction.model.Characteristic;
import com.ajla.auction.model.NumberOfProductsInfo;
import com.ajla.auction.service.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/characteristic")
public class CharacteristicController {
    private final CharacteristicService characteristicService;

    @Autowired
    public CharacteristicController(final CharacteristicService characteristicService) {
        Objects.requireNonNull(characteristicService, "characteristicService must not be null.");
        this.characteristicService = characteristicService;
    }

    @GetMapping("/allCharacteristics")
    public ResponseEntity<List<Characteristic>> getBidsOfProduct() {
        return new ResponseEntity<>(characteristicService.listOfAllCharacteristic(), HttpStatus.OK);
    }
    @GetMapping("/characteristic")
    public ResponseEntity<NumberOfProductsInfo> characteristic(@RequestParam("name") final String name) {
        return new ResponseEntity<>(characteristicService.characteristic(name), HttpStatus.OK);
    }

}
