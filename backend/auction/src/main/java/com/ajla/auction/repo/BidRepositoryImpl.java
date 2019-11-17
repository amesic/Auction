package com.ajla.auction.repo;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class BidRepositoryImpl implements BidRepositoryCustom {
    final EntityManager em;

    @Autowired
    public BidRepositoryImpl(final EntityManager em) {
        this.em = em;
    }

    @Override
    public Boolean checkIfThereIsGreaterValue(final Long valueFromUser, final Product product) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Bid> cq = cb.createQuery(Bid.class);
        final Root<Bid> bid = cq.from(Bid.class);
        cq.where(cb.equal(bid.get("product"), product.getId()));

        TypedQuery<Bid> query = em.createQuery(cq);

        if (query.getResultList().isEmpty()) {
            return (product.getId() > valueFromUser);
        }
        cq.where(cb.and(cb.equal(bid.get("product"), product.getId()), cb.greaterThanOrEqualTo(bid.get("value"), valueFromUser)));
        query = em.createQuery(cq);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }
}
