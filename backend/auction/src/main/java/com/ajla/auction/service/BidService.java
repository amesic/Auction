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

@Service
public class BidService implements IBidService{
    private final BidRepository bidRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public BidService(final BidRepository bidRepository,
                      final ProductRepository productRepository,
                      final UserRepository userRepository) {
        this.bidRepository = bidRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<BidInfo> bidInfo(Long id) {
        BidInfo bi = new BidInfo();
        List<Bid> b = bidRepository.findByProductIdOrderByDateAsc(id);
        Product p;
        if(b.size() == 0) {
            bi.setHighestBid(null);
            bi.setNumberOfBids(null);
            bi.setBidsOfProduct(null);
            p = productRepository.findProductById(id);
        }
        else {
            bi.setBidsOfProduct(b);
            bi.setNumberOfBids((long) b.size());
            Long maxValue = b.get(0).getValue();
            Bid highestBid = b.get(0);
            for (Bid bid : b) {
                if (bid.getValue() > maxValue) {
                    maxValue = bid.getValue();
                    highestBid = bid;
                }
            }
            bi.setHighestBid(highestBid);
            p = b.get(0).getProduct();
        }
        Period period = Period.between(LocalDate.now(), p.getEndDate());
        bi.setDays(period.getDays());
        bi.setMonths(period.getMonths());
        bi.setYears(period.getYears());
        return new ResponseEntity<>(bi, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Bid> saveBidFromUser(Long idProduct, String emailUser, Long value, Long highestValue) {
            if (value <= highestValue) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Bid b = new Bid();
            b.setUser(userRepository.findByEmail(emailUser));
            b.setProduct(productRepository.findProductById(idProduct));
            b.setValue(value);
            b.setDate(LocalDate.now());
            bidRepository.save(b);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Bid> findBidFromUser(String emailUser, Long idProduct) {
        List<Bid> bids = bidRepository.findBidByUserIdAndProductIdOrderByValueDesc(userRepository.findByEmail(emailUser).getId(), idProduct);
        if (bids == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bidRepository.findBidByUserIdAndProductIdOrderByValueDesc(userRepository.findByEmail(emailUser).getId(), idProduct).get(0), HttpStatus.OK);
    }
}
