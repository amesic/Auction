package com.ajla.auction.repo;

import com.ajla.auction.model.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
    Characteristic findCharacteristicById(Long id);

    Characteristic findCharacteristicByName(String name);
}
