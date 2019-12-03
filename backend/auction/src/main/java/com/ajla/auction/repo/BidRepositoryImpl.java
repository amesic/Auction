package com.ajla.auction.repo;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Objects;

public class BidRepositoryImpl implements BidRepositoryCustom {
    final EntityManager em;

    @Autowired
    public BidRepositoryImpl(final EntityManager em) {
        Objects.requireNonNull(em, "em must not be null.");
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
    @Override
    public BidInfo getBidsOfPage(final Long pageNumber, final Long size, final Long idProduct) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Bid> cq = cb.createQuery(Bid.class);
        final Root<Bid> bid = cq.from(Bid.class);

        cq.where(cb.equal(bid.get("product"), idProduct))
                .orderBy(cb.desc(bid.get("value")));

        final TypedQuery<Bid> query = em.createQuery(cq);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        //set total number of last chance products
        final Long totalNumberOfItems = new Long(query.getResultList().size());
        final Bid highestBid = query.getResultList().get(0);
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        query.setFirstResult(Math.toIntExact(pageNumber * size));
        query.setMaxResults(Math.toIntExact(size));

            return new BidInfo(size, pageNumber, totalNumberOfItems,
                    query.getResultList(), highestBid);

    }
}
