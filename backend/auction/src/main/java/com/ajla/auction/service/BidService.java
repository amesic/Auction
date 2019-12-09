package com.ajla.auction.service;

import com.ajla.auction.config.JwtTokenUtil;
import com.ajla.auction.model.Bid;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.model.Product;
import com.ajla.auction.model.User;
import com.ajla.auction.repo.BidRepository;
import com.ajla.auction.repo.ProductRepository;
import com.ajla.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class BidService implements IBidService{
    private final BidRepository bidRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public BidService(final BidRepository bidRepository,
                      final ProductRepository productRepository,
                      final UserRepository userRepository,
                      final JwtTokenUtil jwtTokenUtil) {
        Objects.requireNonNull(jwtTokenUtil, "jwtTokenUtil must not be null.");
        Objects.requireNonNull(bidRepository, "bidRepository must not be null.");
        Objects.requireNonNull(productRepository, "productRepository must not be null.");
        Objects.requireNonNull(userRepository, "userRepository must not be null.");
        this.jwtTokenUtil = jwtTokenUtil;
        this.bidRepository = bidRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BidInfo bidsOfProduct(final Long pageNumber, final Long size, final Long idProduct) {
        BidInfo bidInfo = bidRepository.getBidsOfPage(pageNumber, size, idProduct);
        return bidInfo;
    }
    @Override
    public Bid saveBidFromUser(final Long idProduct, final String emailUser, final Long value) {
        final Product product = productRepository.findProductById(idProduct);
        final User user = userRepository.findByEmail(emailUser);
        if(user != null && productRepository.userIsSellerOfProduct(user.getId(), product.getId())) {
            return null;
        }
            if(user != null && !bidRepository.checkIfThereIsGreaterValue(value, product)) {
                Bid savedBid = new Bid();
                savedBid.setUser(user);
                savedBid.setProduct(product);
                savedBid.setValue(value);
                savedBid.setDate(LocalDate.now());
                bidRepository.save(savedBid);
                return savedBid;
            }
            return null;
    }
    public Long numberOfBidsByProduct(Long productId) {
        return bidRepository.numberOfBidsByProduct(productId);
    }
}
