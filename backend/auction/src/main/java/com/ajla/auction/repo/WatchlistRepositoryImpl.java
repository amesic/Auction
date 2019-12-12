package com.ajla.auction.repo;

import com.ajla.auction.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WatchlistRepositoryImpl implements WatchlistRepositoryCustom {
    private final EntityManager em;
    private final BidRepository bidRepository;

    @Autowired
    public WatchlistRepositoryImpl(final EntityManager em,
                                 final BidRepository bidRepository) {
        Objects.requireNonNull(em, "em must not be null.");
        Objects.requireNonNull( bidRepository, " bidRepository must not be null.");
        this.em = em;
        this.bidRepository = bidRepository;
    }

    @Override
    public PaginationInfo<ProductInfoBid> findWatchlistByUser(final Long idUser, final Long pageNumber, final Long size) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Watchlist> cq = cb.createQuery(Watchlist.class);
        final Root<Watchlist> watchlist = cq.from(Watchlist.class);

        final CriteriaQuery<Long> cqForWatchlistSize = cb.createQuery(Long.class);
        final Root<Watchlist> watchlistForWatchlistSize = cqForWatchlistSize.from(Watchlist.class);

        cqForWatchlistSize.select(cb.count(watchlistForWatchlistSize))
                .where(cb.equal(watchlistForWatchlistSize.get("user"), idUser));

        cq.where(cb.equal(watchlist.get("user"), idUser));

        final TypedQuery<Watchlist> queryForListWatchlist = em.createQuery(cq);
        //pageNumber starts from 0, return list of size number elements, starting from pageNumber*size index of element
        queryForListWatchlist.setFirstResult(Math.toIntExact(pageNumber * size));
        queryForListWatchlist.setMaxResults(Math.toIntExact(size));
        final List<Watchlist> listOfWatchlist = queryForListWatchlist.getResultList();
        final List<ProductInfoBid> listOfProductsInfoBid = new ArrayList<>();
        listOfWatchlist.forEach(w -> {
            final List<Bid> bidsOfProduct = bidRepository.bidsByProduct(w.getProduct().getId());
            ProductInfoBid pib = new ProductInfoBid();
            if (!bidsOfProduct.isEmpty()) {
                pib.setHighestBid(bidsOfProduct.get(0).getValue());
                pib.setNumberOfBids((long) bidsOfProduct.size());
            } else {
                pib.setHighestBid(null);
                pib.setNumberOfBids(null);
            }
            pib.setEndDate(w.getProduct().getEndDate());
            pib.setImages(w.getProduct().getImages());
            pib.setId(w.getProduct().getId());
            pib.setTitle(w.getProduct().getTitle());
            listOfProductsInfoBid.add(pib);
        });
        //set total number of last chance products
        final TypedQuery<Long> queryForWatchlistSize = em.createQuery(cqForWatchlistSize);
        final Long totalNumberOfItems = queryForWatchlistSize.getSingleResult();

        return new PaginationInfo<>(size, pageNumber, totalNumberOfItems, listOfProductsInfoBid);

    }
    @Override
    public Watchlist getWatchlistByProductIdAndUserId(final Long idUser, final Long idProduct) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Watchlist> cq = cb.createQuery(Watchlist.class);
        final Root<Watchlist> watchlist = cq.from(Watchlist.class);

        cq.where(cb.and(
                cb.equal(watchlist.get("user"), idUser),
                cb.equal(watchlist.get("product"), idProduct)));
        TypedQuery<Watchlist> query = em.createQuery(cq);

        if (!query.getResultList().isEmpty()) {
            return query.getResultList().get(0);
        }

        return null;
    }
}
