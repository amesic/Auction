package com.ajla.auction.service;

import com.ajla.auction.model.Rate;
import com.ajla.auction.model.User;
import com.ajla.auction.repo.RateRepository;
import com.ajla.auction.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RateService implements IRateService{
    private final UserRepository userRepository;
    private final RateRepository rateRepository;

    public RateService(final UserRepository userRepository,
                       final RateRepository rateRepository) {
        Objects.requireNonNull(userRepository, "userRepository must not be null.");
        Objects.requireNonNull(rateRepository, "rateRepository must not be null.");
        this.userRepository = userRepository;
        this.rateRepository = rateRepository;
    }

    @Override
    public Double getRatingOfSeller(final String emailSeller) {
        User seller = userRepository.findByEmail(emailSeller);
        return rateRepository.ratingOfSeller(seller);
    }

    @Override
    public Rate saveRateFromUser(final Rate rate) {
        User user = userRepository.findByEmail(rate.getUser().getEmail());
        User seller = userRepository.findByEmail(rate.getSeller().getEmail());
        Rate savedRate = new Rate();
        savedRate.setUser(user);
        savedRate.setSeller(seller);
        savedRate.setValue(rate.getValue());
        rateRepository.save(savedRate);
        return savedRate;
    }
}
