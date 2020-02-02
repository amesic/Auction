package com.ajla.auction.repo;

import com.ajla.auction.model.Rate;
import com.ajla.auction.model.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class RateRepositoryImpl implements RateRepositoryCustom {
    private final EntityManager em;

    public RateRepositoryImpl(final EntityManager em) {
        Objects.requireNonNull(em, "em must not be null.");
        this.em = em;
    }

    @Override
    public Double ratingOfSeller(User seller) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Double> cq = cb.createQuery(Double.class);
        final Root<Rate> rate = cq.from(Rate.class);

        cq.select(cb.avg(rate.get("value")))
                .where(cb.equal(rate.get("seller"), seller));

        return em.createQuery(cq).getSingleResult();
    }
}
