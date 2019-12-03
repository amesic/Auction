package com.ajla.auction.repo;

import com.ajla.auction.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
