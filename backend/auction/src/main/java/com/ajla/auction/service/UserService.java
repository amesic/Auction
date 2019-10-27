package com.ajla.auction.service;

import com.ajla.auction.model.User;
import com.ajla.auction.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService implements IUserService{
    @Autowired
    public UserRepo repo;

    @Override
    public Long findByEmailPassword(String email, String password) {
        Iterable<User> users=repo.findAll();
        for(User user: users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password))
                return user.getId();
        }
        return null;
    }
    @Override
    public Long findByEmail(String email){
        Iterable<User> users=repo.findAll();
        for(User user: users){
            if(user.getEmail().equals(email))
                return user.getId();
        }
        return null;
    }
    @Override
    public ResponseEntity<String> saveDataFromUser (User user){
        Long id=findByEmail(user.getEmail());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        if(id==null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            //encoder.matches(password, user.getPassword());  ovako provjeravamo jel isti
            repo.save(user);
            return new ResponseEntity<>("You are successfully registered " + user.getUserName() + "!", headers, HttpStatus.OK);
        }
        return new ResponseEntity<>("You are already registered with " + user.getEmail() + " email!", headers, HttpStatus.BAD_REQUEST);
    }
}
