package com.ajla.auction.repo;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.User;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Product;
import com.ajla.auction.model.UserProductInfoBid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BidRepositoryImpl implements BidRepositoryCustom {
    private final EntityManager em;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public BidRepositoryImpl(final EntityManager em,
                             final TransactionTemplate transactionTemplate) {
        Objects.requireNonNull(em, "em must not be null.");
        Objects.requireNonNull(transactionTemplate, "transactionTemplate must not be null.");
        this.em = em;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Boolean checkIfThereIsGreaterValue(final Double valueFromUser, final Product product) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Bid> cq = cb.createQuery(Bid.class);
        final Root<Bid> bid = cq.from(Bid.class);
        cq.where(cb.equal(bid.get("product"), product.getId()));

        TypedQuery<Bid> query = em.createQuery(cq);

        if (query.getResultList().isEmpty()) {
            return (product.getStartPrice() > valueFromUser);
        }
        cq.where(cb.and(cb.equal(bid.get("product"), product.getId()), cb.greaterThanOrEqualTo(bid.get("value"), valueFromUser)));
        query = em.createQuery(cq);
        return !query.getResultList().isEmpty();
    }

    @Override
    public PaginationInfo<Bid> getProductBids(final Long pageNumber,
                                              final Long size,
                                              final Long idProduct) {
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

            return new PaginationInfo<>(size, pageNumber, totalNumberOfItems, listOfBids);
    }

    @Override
    public Long numberOfBidsForProduct(final Long productId) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cqForBidSize = cb.createQuery(Long.class);
        final Root<Bid> bidForBidSize = cqForBidSize.from(Bid.class);

        cqForBidSize.select(cb.count(bidForBidSize.get("product")))
                .where(cb.equal(bidForBidSize.get("product"), productId))
                .groupBy(bidForBidSize.get("product"));
        final TypedQuery<Long> queryForNumberOfBids = em.createQuery(cqForBidSize);
        return queryForNumberOfBids.getSingleResult();
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

    @Override
    public PaginationInfo<UserProductInfoBid> getUserBids(final Long pageNumber, final Long size, final Long userId) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Bid> cqForBid = cb.createQuery(Bid.class);
        final Root<Bid> bid = cqForBid.from(Bid.class);

        CriteriaQuery<Long> cqForBidSize = cb.createQuery(Long.class);
        final Root<Bid> bidForBidSize = cqForBidSize.from(Bid.class);

        cqForBid.where(cb.equal(bid.get("user"), userId));

        TypedQuery<Bid> userBidsProductQuery = em.createQuery(cqForBid);
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        userBidsProductQuery.setFirstResult(Math.toIntExact(pageNumber * size));
        userBidsProductQuery.setMaxResults(Math.toIntExact(size));

        List<Bid> userBidsProduct = userBidsProductQuery.getResultList();
        List<UserProductInfoBid> userProductInfoBids = new ArrayList<>();

        userBidsProduct.forEach(ubp -> {
            UserProductInfoBid userProductInfoBid = new UserProductInfoBid();

            cqForBid.where(cb.equal(bid.get("product"), ubp.getProduct().getId()))
                    .orderBy(cb.desc(bid.get("value")));
            TypedQuery<Bid> bidsProductQuery = em.createQuery(cqForBid);
            //only first bid is returned, because it is the highest
            bidsProductQuery.setFirstResult(0);
            bidsProductQuery.setMaxResults(1);
            Bid highestBid = bidsProductQuery.getSingleResult();
            userProductInfoBid.setHighestBid(highestBid.getValue());

            cqForBidSize.select(cb.count(bidForBidSize))
                    .where(cb.equal(bidForBidSize.get("product"), ubp.getProduct().getId()));

            userProductInfoBid.setTitle(ubp.getProduct().getTitle());
            userProductInfoBid.setId(ubp.getProduct().getId());
            userProductInfoBid.setEndDate(ubp.getProduct().getEndDate());
            userProductInfoBid.setImages(ubp.getProduct().getImages());
            userProductInfoBid.setNumberOfBids(em.createQuery(cqForBidSize).getSingleResult());
            userProductInfoBid.setValueOfBid(ubp.getValue());

            userProductInfoBids.add(userProductInfoBid);
        });

        cqForBidSize.select(cb.count(bidForBidSize))
                .where(cb.equal(bidForBidSize.get("user"), userId));
        final TypedQuery<Long> queryForBidSize= em.createQuery(cqForBidSize);
        final Long totalNumberOfItems = queryForBidSize.getSingleResult();

        return new PaginationInfo<>(
                size,
                pageNumber,
                totalNumberOfItems,
                userProductInfoBids
        );
    }

    @Override
    public Bid saveBidFromUser(final User user, final Product product, final Double value) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Bid> cq = cb.createQuery(Bid.class);
        final Root<Bid> bid = cq.from(Bid.class);

        cq.where(cb.and(
                cb.equal(bid.get("user"), user.getId()),
                cb.equal(bid.get("product"), product.getId())
        ));

        TypedQuery<Bid> query = em.createQuery(cq);
        List<Bid> bidFromUserByProduct = query.getResultList();
        Bid savedBid = new Bid();
        if (bidFromUserByProduct.isEmpty()) {
            savedBid.setUser(user);
            savedBid.setProduct(product);
            savedBid.setValue(value);
            savedBid.setDate(LocalDate.now());
            return savedBid;
        } else {
            final CriteriaUpdate<Bid> cqForUpdate = cb.createCriteriaUpdate(Bid.class);
            final Root<Bid> bidForUpdate = cqForUpdate.from(Bid.class);

            cqForUpdate.set(bidForUpdate.get("value"), value);
            cqForUpdate.set(bidForUpdate.get("date"), LocalDate.now());

            cqForUpdate.where(cb.and(
                    cb.equal(bidForUpdate.get("user"), user.getId()),
                    cb.equal(bidForUpdate.get("product"), product.getId())
            ));

            transactionTemplate.execute(transactionStatus -> {
                em.createQuery(cqForUpdate).executeUpdate();
                transactionStatus.flush();
                return null;
            });
        }
        return null;
    }

}
