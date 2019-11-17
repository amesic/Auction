package com.ajla.auction.service;

import com.ajla.auction.model.User;
import com.ajla.auction.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.Objects;


@Service
public class UserService implements IUserService, UserDetailsService {
    private final UserRepository userRepository;

    //we need qualifier when have more implementations of userRepo
    //for example Smth1 implements userRepo, Smth2 implements userRepo, and then we have two beans
    @Autowired
    public UserService(final UserRepository userRepository) {
        Objects.requireNonNull(userRepository, "userRepository must not be null.");
        this.userRepository = userRepository;
    }

    @Override
    public Long findByEmail(final String email) {
       final User user = userRepository.findByEmail(email);
       if (user == null) {
           return null;
       }
       return user.getId();
    }
    @Override
    public Boolean saveDataFromUser(final User user) {
        final Long id = findByEmail(user.getEmail());
        if (id == null) {
            final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            //encoder.matches(password, user.getPassword());  ovako provjeravamo jel isti
            userRepository.save(user);
            return false;
        }
        return true;
    }
    //for auth looking for user from database
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                new ArrayList<>());
    }
}
