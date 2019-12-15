package com.ajla.auction.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    final EntityManager em;

    @Autowired
    public UserRepositoryImpl(final EntityManager em){
        Objects.requireNonNull(em, "em must not be null.");
        this.em = em;
    }

}
