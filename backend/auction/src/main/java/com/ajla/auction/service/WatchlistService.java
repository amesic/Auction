package com.ajla.auction.service;

import com.ajla.auction.model.Product;
import com.ajla.auction.model.ProductInfoBid;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.User;
import com.ajla.auction.model.Watchlist;
import com.ajla.auction.repo.ProductRepository;
import com.ajla.auction.repo.UserRepository;
import com.ajla.auction.repo.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WatchlistService implements IWatchlistService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final WatchlistRepository watchlistRepository;

    @Autowired
    public WatchlistService(final ProductRepository productRepository,
                            final UserRepository userRepository, WatchlistRepository watchlistRepository) {
        Objects.requireNonNull(productRepository,"productRepository must not be null.");
        Objects.requireNonNull(userRepository,"userRepository must not be null.");
        Objects.requireNonNull(watchlistRepository,"watchlistRepository must not be null.");
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.watchlistRepository = watchlistRepository;
    }

    @Override
    public Watchlist saveNewProductInfoWatchlistOfUser(final String email, final Long idProduct) {
        final Product product = productRepository.findProductById(idProduct);
        final User user = userRepository.findByEmail(email);
        if (watchlistRepository.getWatchlistByProductIdAndUserId(user.getId(), product.getId()) == null) {
            Watchlist w = new Watchlist();
            w.setUser(user);
            w.setProduct(product);
            watchlistRepository.save(w);
            return w;
        } else {
            return null;
        }
    }
    @Override
    public PaginationInfo<ProductInfoBid> findWatchlistByUser(final String email, final Long pageNumber, final Long size) {
        final Long idUser = userRepository.findByEmail(email).getId();
       return watchlistRepository.findWatchlistByUser(idUser, pageNumber, size);
    }
    @Override
    public PaginationInfo<ProductInfoBid> deleteItemFromWatchlist(final String email,
                                                                  final Long idProduct,
                                                                  final Long pageNumber,
                                                                  final Long size) {
        final Long idUser = userRepository.findByEmail(email).getId();
        return watchlistRepository.deleteItemFromWatchlist(idUser, idProduct, pageNumber, size);
    }

}
