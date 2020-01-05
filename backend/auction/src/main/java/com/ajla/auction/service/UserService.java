package com.ajla.auction.service;

import com.ajla.auction.model.Card;
import com.ajla.auction.model.User;
import com.ajla.auction.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
    public User findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean saveDataFromUser(final User user) {
        final User userWithEmail = findByEmail(user.getEmail());
        if (userWithEmail == null) {
            if (user.getEmail().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$")
                    && user.getPassword().length() >= 8
                    && user.getUserName() != null && !user.getUserName().equals("")) {
                final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return false;
            }
            return null;
        }
        return true;
    }

    //for auth looking for user from database
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    @Override
    public void saveCardId(final Card card, final String email) {
        User user = findByEmail(email);
        user.setCard(card);
        userRepository.save(user);
    }
}
