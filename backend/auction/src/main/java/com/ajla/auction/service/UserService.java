package com.ajla.auction.service;

import com.ajla.auction.model.User;
import com.ajla.auction.repo.UserRepo;

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


@Service
public class UserService implements IUserService, UserDetailsService {
    //properties
    private final UserRepo userRepo;

    //dependency injection
    //we need qualifier when have more implementations of userRepo
    //for example Smth1 implements userRepo, Smth2 implements userRepo,and then we have two beans
    @Autowired
    public UserService(final UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public Long findByEmailPassword(final String email, final String password) {
        final Iterable<User> users = userRepo.findAll();
        for (final User user: users){
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user.getId();
            }
        }
        return null;
    }
    @Override
    public Long findByEmail(final String email){
       final User user = userRepo.findByEmail(email);
       if (user == null) {
           return null;
       }
       return user.getId();
    }
    @Override
    public ResponseEntity<String> saveDataFromUser(final User user){
        final Long id = findByEmail(user.getEmail());
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        if (id == null) {
            final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            //encoder.matches(password, user.getPassword());  ovako provjeravamo jel isti
            userRepo.save(user);
            return new ResponseEntity<>("You are successfully registered " + user.getUserName() + "!", headers, HttpStatus.OK);
        }
        return new ResponseEntity<>("You are already registered with " + user.getEmail() + " email!", headers, HttpStatus.BAD_REQUEST);
    }
    //for auth looking for user from database
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                new ArrayList<>());
    }
}
