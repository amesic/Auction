package com.ajla.auction.controller;

import com.ajla.auction.config.JwtTokenUtil;
import com.ajla.auction.model.Bid;
import com.ajla.auction.model.CardInfo;
import com.ajla.auction.model.User;
import com.ajla.auction.model.Rate;
import com.ajla.auction.model.Card;
import com.ajla.auction.model.Product;
import com.ajla.auction.model.ProductInfoBid;
import com.ajla.auction.model.RequiredInfoUser;
import com.ajla.auction.model.UserWatchProductId;
import com.ajla.auction.model.Watchlist;
import com.ajla.auction.model.UserProductInfoBid;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.UserBidList;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.service.BidService;
import com.ajla.auction.service.ProductService;
import com.ajla.auction.service.UserService;
import com.ajla.auction.service.RateService;
import com.ajla.auction.service.StripeService;
import com.ajla.auction.service.CardService;
import com.ajla.auction.service.WatchlistService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final BidService bidService;
    private final JwtTokenUtil jwtTokenUtil;
    private final SimpMessagingTemplate template;
    private final ProductController productController;
    private final UserService userService;
    private final ProductService productService;
    private final WatchlistService watchlistService;
    private final StripeService stripeService;
    private final CardService cardService;
    private final RateService rateService;
    private final AuthenticationManager authenticationManager;

    private Bid highestBid;

    @Autowired
    public AuthController(final BidService bidService,
                          final JwtTokenUtil jwtTokenUtil,
                          final SimpMessagingTemplate template,
                          final ProductController productController,
                          final UserService userService, ProductService productService,
                          final WatchlistService watchlistService,
                          final StripeService stripeService,
                          final CardService cardService,
                          final RateService rateService,
                          final AuthenticationManager authenticationManager) {
        Objects.requireNonNull(template, "template must not be null.");
        Objects.requireNonNull(jwtTokenUtil, "jwtTokenUtil must not be null.");
        Objects.requireNonNull(bidService, "bidService must not be null.");
        Objects.requireNonNull(productController, "productController must not be null.");
        Objects.requireNonNull(userService, "userService must not be null.");
        Objects.requireNonNull(productService, "productService must not be null.");
        Objects.requireNonNull(watchlistService, "watchlistService must not be null.");
        Objects.requireNonNull(stripeService, "stripeService must not be null.");
        Objects.requireNonNull(cardService, "cardService must not be null.");
        Objects.requireNonNull(rateService, "rateService must not be null.");
        Objects.requireNonNull(authenticationManager, "authenticationManager must not be null.");
        this.jwtTokenUtil = jwtTokenUtil;
        this.bidService = bidService;
        this.template = template;
        this.productController = productController;
        this.userService = userService;
        this.productService = productService;
        this.watchlistService = watchlistService;
        this.stripeService = stripeService;
        this.cardService = cardService;
        this.rateService = rateService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/bid/newBid")
    public ResponseEntity<Bid> saveBidFromUser(@RequestBody final Bid bid,
                                               final HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        if (!requestTokenHeader.equals("")) {
            jwtToken = requestTokenHeader.substring(7);
            if (!jwtTokenUtil.getUsernameFromToken(jwtToken).equals(bid.getUser().getEmail())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        final Bid savedBid = bidService.saveBidFromUser(
                bid.getProduct().getId(),
                bid.getUser().getEmail(),
                bid.getValue()
        );
        if (savedBid == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.highestBid = savedBid;
        return new ResponseEntity<>(savedBid, HttpStatus.OK);
    }

    @GetMapping("/user/info")
    public ResponseEntity<User> getUserInformation(@RequestParam("email") final String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @MessageMapping("/send/message/bidsOfUser")
    @SendToUser
    public void onMyAccountBidsList(final UserBidList userBidList) {
        /*SimpMessageHeaderAccessor headerAcc = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAcc.setSessionId(userBidList.getSessionId());
        headerAcc.setLeaveMutable(true);
        userBidList.getBids().forEach(bid -> {

        });
        this.template.convertAndSendToUser(
                userBidList.getSessionId(),
                "/notify/timeLeft",
                bidInfo,
                headerAcc.getMessageHeaders()
                );*/
    }

    @MessageMapping("/send/message/highestBid")
    @SendToUser
    public void onHighestBidNotify(final UserWatchProductId userProduct) {
        this.productController.usersWatchingProduct.forEach(uw -> {
            if (uw.getProductId().equals(userProduct.getProductId()) && !uw.getSessionId().equals(userProduct.getSessionId())) {
                SimpMessageHeaderAccessor headerAcc = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                headerAcc.setSessionId(uw.getSessionId());
                headerAcc.setLeaveMutable(true);
                BidInfo bidInfo = new BidInfo(
                        (long) 0,
                        (long) 0,
                        bidService.numberOfBidsByProduct(userProduct.getProductId()),
                        Collections.emptyList(),
                        highestBid
                );
                this.template.convertAndSendToUser(
                        uw.getSessionId(),
                        "/queue/highestBid",
                        bidInfo,
                        headerAcc.getMessageHeaders()
                );
            }
        });
    }

    @GetMapping("/product/sold")
    public ResponseEntity<PaginationInfo<ProductInfoBid>> getSoldProductsByUserSeller(
            @RequestParam("email") final String email,
            @RequestParam("pageNumber") final Long pageNumber,
            @RequestParam("size") final Long size) {
        return new ResponseEntity<>(productService.getAllSoldProductsOfSeller(email, pageNumber, size), HttpStatus.OK);
    }

    @GetMapping("/product/active")
    public ResponseEntity<PaginationInfo<ProductInfoBid>> getActiveProductsByUserSeller(
            @RequestParam("email") final String email,
            @RequestParam("pageNumber") final Long pageNumber,
            @RequestParam("size") final Long size) {
        return new ResponseEntity<>(
                productService.getAllActiveProductsOfSeller(email, pageNumber, size),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/save/watchlist")
    public ResponseEntity<Watchlist> saveNewProductFromUserIntoWatchlist(@RequestBody final Watchlist watchlist) {
        final Watchlist savedWatchlist = watchlistService.saveNewProductInfoWatchlistOfUser(
                watchlist.getUser().getEmail(),
                watchlist.getProduct().getId()
        );
        if (savedWatchlist != null) {
            return new ResponseEntity<>(savedWatchlist , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/watchlistFromUser")
    public ResponseEntity<PaginationInfo<ProductInfoBid>> getWatchlistOfProduct(
            @RequestParam("email") final String email,
            @RequestParam("pageNumber") final Long pageNumber,
            @RequestParam("size") final Long size) {
        return new ResponseEntity<>(watchlistService.findWatchlistByUser(email, pageNumber, size), HttpStatus.OK);
    }

    @DeleteMapping("/deleteItemFromWatchlist")
    public ResponseEntity<PaginationInfo<ProductInfoBid>> deleteItemFromWatchlist(
            @RequestParam("email") final String email,
            @RequestParam("idProduct") final Long idProduct,
            @RequestParam("pageNumber") final Long pageNumber,
            @RequestParam("size") final Long size) {
        return new ResponseEntity<>(
                watchlistService.deleteItemFromWatchlist(email, idProduct, pageNumber, size),
                HttpStatus.OK
        );
    }

    @GetMapping("/bid/user")
    public ResponseEntity<PaginationInfo<UserProductInfoBid>> getSoldProductsByUserSeller(
            @RequestParam("pageNumber") final Long pageNumber,
            @RequestParam("size") final Long size,
            @RequestParam("email") final String email) {
        return new ResponseEntity<>(bidService.bidsOfUser(pageNumber, size, email), HttpStatus.OK);
    }

    @PostMapping("/card/user")
    public ResponseEntity<?> saveCardFromUser(@RequestBody final CardInfo cardInfo) {
        User user = userService.findByEmail(cardInfo.getEmailUser());

        if (user.getCard() != null) {
            try {
                CardInfo cardUpdated = stripeService.updateCustomer(user.getCard().getCustomerId(), cardInfo);
                if (user.getSeller()) {
                    try {
                        String accountId = stripeService.createStripeAccountForSeller(user, cardInfo);
                        cardService.saveAccountId(user.getCard().getId(), accountId);
                    } catch(StripeException ex) {
                        return new ResponseEntity<>(ex.getCode(), HttpStatus.BAD_REQUEST);
                    }
                }
                return new ResponseEntity<>(cardUpdated, HttpStatus.OK);
            } catch(StripeException ex) {
                return new ResponseEntity<>(ex.getCode(), HttpStatus.BAD_REQUEST);
            }
        } else {
            try {
                CardInfo cardSavedInfo = stripeService.createCustomer(cardInfo);
                Card savedCard = cardService.saveCustomerId(cardSavedInfo.getCustomerId());
                userService.saveCardId(savedCard, cardInfo.getEmailUser());
                if (user.getSeller()) {
                    try {
                        String accountId = stripeService.createStripeAccountForSeller(user, cardInfo);
                        cardService.saveAccountId(savedCard.getId(), accountId);
                    } catch(StripeException ex) {
                        return new ResponseEntity<>(ex.getCode(), HttpStatus.BAD_REQUEST);
                    }
                }
                return new ResponseEntity<>(cardSavedInfo, HttpStatus.OK);
            } catch(StripeException ex) {
                return new ResponseEntity<>(ex.getCode(), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/card/info/user")
    public ResponseEntity<?> getUserCardInfo(@RequestParam("email") final String email) {
       User user = userService.findByEmail(email);
       if (user.getCard() != null) {
           String customerId = user.getCard().getCustomerId();
           try {
               CardInfo cardInfo = stripeService.getUserCardDetails(customerId);
               return new ResponseEntity<>(cardInfo, HttpStatus.OK);
           } catch (StripeException ex) {
               return new ResponseEntity<>(ex.getCode(), HttpStatus.BAD_REQUEST);
           }
       } else {
           return new ResponseEntity<>(null, HttpStatus.OK);
       }
    }

    @GetMapping("/card/charge")
    public ResponseEntity<?> chargeCustomer(@RequestParam("emailCustomer") final String emailCustomer,
                                            @RequestParam("emailSeller") final String emailSeller,
                                            @RequestParam("productId") final Long productId,
                                            @RequestParam("amount") final int amount) {
        User customer = userService.findByEmail(emailCustomer);
        User seller = userService.findByEmail(emailSeller);
        Product product = productService.findSingleProduct(productId);
        try {
            String chargeId = stripeService.createCharge(
                    customer.getCard().getCustomerId(),
                    seller.getCard().getAccountId(),
                    product.getTitle(),
                    amount
            );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (StripeException ex) {
            return new ResponseEntity<>(ex.getCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/rating")
    public ResponseEntity<?> chargeCustomer(@RequestParam("emailSeller") final String emailSeller) {
        return new ResponseEntity<>(rateService.getRatingOfSeller(emailSeller), HttpStatus.OK);
    }

    @PostMapping("/rate")
    public ResponseEntity<?> saveRateFromUser(@RequestBody final Rate rate) {
        return new ResponseEntity<>(rateService.saveRateFromUser(rate), HttpStatus.OK);
    }

    @PostMapping("/user/required/info")
    public ResponseEntity<?> saveRequiredInfoFromUser(@RequestBody final RequiredInfoUser requiredInfoUser) throws Throwable {
        try {
            User savedUser = userService.saveUserRequiredInfo(requiredInfoUser);
            RequiredInfoUser savedUserInfo = new RequiredInfoUser();
            savedUserInfo.setEmail(savedUser.getEmail());
            savedUserInfo.setGender(savedUser.getGender());
            savedUserInfo.setBirthDate(savedUser.getBirthDate());
            savedUserInfo.setPhoneNumber(savedUser.getPhoneNumber());
            savedUserInfo.setUserName(savedUser.getUserName());
            savedUserInfo.setImage(savedUser.getImage());
            if (!requiredInfoUser.getEmail().equals(requiredInfoUser.getEmailLoggedUser())) {
                final UserDetails userDetails = userService.loadUserByUsername(savedUser.getEmail());
                final String token = jwtTokenUtil.generateToken(userDetails);
                savedUserInfo.setToken(token);
            }
            return new ResponseEntity<>(savedUserInfo, HttpStatus.OK);
        } catch (Throwable ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/address/info")
    public ResponseEntity<?> saveAddressInfo(@RequestBody final User user) {
        return new ResponseEntity<>(userService.saveAddressOfUser(user), HttpStatus.OK);
    }

}
