package com.ajla.auction.service;

import com.ajla.auction.model.*;
import com.ajla.auction.repo.BidRepository;
import com.ajla.auction.repo.ProductRepository;
import com.ajla.auction.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        Objects.requireNonNull(productRepository, "productRepository must not be null.");
        Objects.requireNonNull(userRepository, "userRepository must not be null.");
        this.bidRepository = bidRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PaginationInfo<Bid> bidsOfProduct(final Long pageNumber, final Long size, final Long idProduct) {
        return bidRepository.getProductBids(pageNumber, size, idProduct);
    }

    @Override
    public Bid saveBidFromUser(final Long idProduct, final String emailUser, final Double value) {
        final Product product = productRepository.findProductById(idProduct);
        final User user = userRepository.findByEmail(emailUser);
        if(user != null && productRepository.userIsSellerOfProduct(user.getId(), product.getId())) {
            return null;
        }
            if(user != null && !bidRepository.checkIfThereIsGreaterValue(value, product)) {
               final Bid savedBid = bidRepository.saveBidFromUser(user, product, value);
               if (savedBid != null) {
                   bidRepository.save(savedBid);
                   return savedBid;
               } else {
                   Bid saved = new Bid();
                   saved.setUser(user);
                   saved.setProduct(product);
                   saved.setValue(value);
                   saved.setDate(LocalDate.now());
                   return saved;
               }
            }
            return null;
    }

    public Long numberOfBidsByProduct(Long productId) {
        return bidRepository.numberOfBidsForProduct(productId);
    }

    @Override
    public PaginationInfo<UserProductInfoBid> bidsOfUser(final Long pageNumber, final Long size, final String email) {
        return bidRepository.getUserBids(pageNumber, size, userRepository.findByEmail(email).getId());
    }
}
