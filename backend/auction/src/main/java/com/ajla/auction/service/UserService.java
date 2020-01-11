package com.ajla.auction.service;

import com.ajla.auction.model.Address;
import com.ajla.auction.model.Card;
import com.ajla.auction.model.RequiredInfoUser;
import com.ajla.auction.model.User;
import com.ajla.auction.repo.AddressRepository;
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
    private final AddressRepository addressRepository;

    //we need qualifier when have more implementations of userRepo
    //for example Smth1 implements userRepo, Smth2 implements userRepo, and then we have two beans
    @Autowired
    public UserService(final UserRepository userRepository,
                       final AddressRepository addressRepository) {
        Objects.requireNonNull(userRepository, "userRepository must not be null.");
        Objects.requireNonNull(addressRepository, "addressRepository must not be null.");
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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
                user.setSeller(false);
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

    @Override
    public User saveUserRequiredInfo(final RequiredInfoUser requiredInfoUser) throws Throwable {
        if (requiredInfoUser.getEmail().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$")
                && ((findByEmail(requiredInfoUser.getEmail()) != null && requiredInfoUser.getEmailLoggedUser().equals(requiredInfoUser.getEmail()))
                || (findByEmail(requiredInfoUser.getEmail()) == null && !requiredInfoUser.getEmailLoggedUser().equals(requiredInfoUser.getEmail())))
        && requiredInfoUser.getUserName() != null && !requiredInfoUser.getUserName().equals("")
        && requiredInfoUser.getBirthDate() != null
        && requiredInfoUser.getGender() != null && !requiredInfoUser.getGender().equals("")
        && requiredInfoUser.getPhoneNumber().matches("^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$")) {
            User user = findByEmail(requiredInfoUser.getEmailLoggedUser());
            user.setPhoneNumber(requiredInfoUser.getPhoneNumber());
            user.setUserName(requiredInfoUser.getUserName());
            user.setGender(requiredInfoUser.getGender());
            user.setBirthDate(requiredInfoUser.getBirthDate());
            if (!requiredInfoUser.getEmailLoggedUser().equals(requiredInfoUser.getEmail())) {
                user.setEmail(requiredInfoUser.getEmail());
            }
            userRepository.save(user);
            return user;
        } else if (!requiredInfoUser.getEmail().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$")) {
            throw new Throwable("Email is invalid!");
        } else if (findByEmail(requiredInfoUser.getEmail()) != null
                && !requiredInfoUser.getEmailLoggedUser().equals(requiredInfoUser.getEmail())) {
            throw new Throwable("Profile with this email already exist!");
        } else if (requiredInfoUser.getUserName() == null || requiredInfoUser.getUserName().equals("")) {
            throw new Throwable("Username input empty!");
        } else if (requiredInfoUser.getBirthDate() == null) {
            throw new Throwable("Birth date input empty!");
        } else if (requiredInfoUser.getGender() == null || requiredInfoUser.getGender().equals("")) {
            throw new Throwable("Gender input empty!");
        } else if (!requiredInfoUser.getPhoneNumber().matches("^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$")) {
            throw new Throwable("Phone number is invalid!");
        }
        return null;

    }

    @Override
    public Address saveAddressOfUser(final User user) {
        User updatedUser = findByEmail(user.getEmail());
        if (updatedUser.getAddress() == null) {
            Address savedAddress = new Address();
            savedAddress.setCity(user.getAddress().getCity());
            savedAddress.setCountry(user.getAddress().getCountry());
            savedAddress.setStreet(user.getAddress().getStreet());
            savedAddress.setState(user.getAddress().getState());
            savedAddress.setZipCode(user.getAddress().getZipCode());

            updatedUser.setAddress(addressRepository.save(savedAddress));
            userRepository.save(updatedUser);
            return savedAddress;
        } else {
            Address addressInfo = user.getAddress();
            addressInfo.setId(updatedUser.getAddress().getId());
            return addressRepository.updateAddressOfUser(addressInfo);
        }
    }

}
