package com.ajla.auction.service;

import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.model.Product;
import com.ajla.auction.repo.BidRepository;
import com.ajla.auction.repo.ProductRepository;
import com.ajla.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@Service
public class BidService implements IBidService{
    private final BidRepository bidRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public BidService(final BidRepository bidRepository,
                      final ProductRepository productRepository,
                      final UserRepository userRepository) {
        Objects.requireNonNull(bidRepository, "bidRepository must not be null.");
        this.bidRepository = bidRepository;
        Objects.requireNonNull(productRepository, "productRepository must not be null.");
        this.productRepository = productRepository;
        Objects.requireNonNull(userRepository, "userRepository must not be null.");
        this.userRepository = userRepository;
    }

    @Override
    public List<Bid> bidsOfProduct(final Long id) {
        List<Bid> bidsOfProduct = bidRepository.findByProductIdOrderByDateAsc(id);
        return bidsOfProduct;
    }
    @Override
    public Bid saveBidFromUser(final Long idProduct, final String emailUser, final Long value) {
        final Product product = productRepository.findProductById(idProduct);
            if(!bidRepository.checkIfThereIsGreaterValue(value, product)) {
                Bid savedBid = new Bid();
                savedBid.setUser(userRepository.findByEmail(emailUser));
                savedBid.setProduct(product);
                savedBid.setValue(value);
                savedBid.setDate(LocalDate.now());
                bidRepository.save(savedBid);
                return savedBid;
            }
            return null;
    }
}
