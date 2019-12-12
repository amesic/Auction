package com.ajla.auction.repo;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.model.Product;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

public class BidRepositoryImpl implements BidRepositoryCustom {
    private final EntityManager em;

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
        return !query.getResultList().isEmpty();
    }
    @Override
    public BidInfo getBidsOfPage(final Long pageNumber, final Long size, final Long idProduct) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Bid> cq = cb.createQuery(Bid.class);
        final Root<Bid> bid = cq.from(Bid.class);

        final CriteriaQuery<Long> cqForBidSize = cb.createQuery(Long.class);
        final Root<Bid> bidForBidSize = cqForBidSize.from(Bid.class);

        cqForBidSize.select(cb.count(bidForBidSize.get("product")))
                .where(cb.equal(bidForBidSize.get("product"), idProduct))
                .groupBy(bidForBidSize.get("product"));

        cq.where(cb.equal(bid.get("product"), idProduct))
                .orderBy(cb.desc(bid.get("value")));

        final TypedQuery<Bid> queryForListBids = em.createQuery(cq);
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        queryForListBids.setFirstResult(Math.toIntExact(pageNumber * size));
        queryForListBids.setMaxResults(Math.toIntExact(size));
        final List<Bid> listOfBids = queryForListBids.getResultList();
        if (listOfBids.size() == 0) {
            return null;
        }
        //set total number of bids
        final TypedQuery<Long> queryForNumberOfBids = em.createQuery(cqForBidSize);
        final Long totalNumberOfItems = queryForNumberOfBids.getSingleResult();
        final Bid highestBid = listOfBids.get(0);

            return new BidInfo(size, pageNumber, totalNumberOfItems,
                    listOfBids, highestBid);

    }
    @Override
    public Long numberOfBidsByProduct(final Long productId) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cqForBidSize = cb.createQuery(Long.class);
        final Root<Bid> bidForBidSize = cqForBidSize.from(Bid.class);

        cqForBidSize.select(cb.count(bidForBidSize.get("product")))
                .where(cb.equal(bidForBidSize.get("product"), productId))
                .groupBy(bidForBidSize.get("product"));
        final TypedQuery<Long> queryForNumberOfBids = em.createQuery(cqForBidSize);
        final Long totalNumberOfItems = queryForNumberOfBids.getSingleResult();
        return  totalNumberOfItems;
    }
    @Override
    public List<Bid> bidsByProduct(final Long productId) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Bid> cqForBid = cb.createQuery(Bid.class);
        final Root<Bid> bidForBid = cqForBid.from(Bid.class);

        cqForBid.where(cb.equal(bidForBid.get("product"), productId))
                .orderBy(cb.desc(bidForBid.get("value")));
        final TypedQuery<Bid> queryForNumberOfBids = em.createQuery(cqForBid);

        return  queryForNumberOfBids.getResultList();
    }
}
