package com.ajla.auction.controller;

import com.ajla.auction.config.JwtTokenUtil;
import com.ajla.auction.model.Bid;
import com.ajla.auction.model.User;
import com.ajla.auction.model.ProductInfoBid;
import com.ajla.auction.model.UserWatchProductId;
import com.ajla.auction.model.Watchlist;
import com.ajla.auction.model.UserProductInfoBid;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.BidInfo;
import com.ajla.auction.service.BidService;
import com.ajla.auction.service.ProductService;
import com.ajla.auction.service.UserService;
import com.ajla.auction.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    private Bid highestBid;

    @Autowired
    public AuthController(final BidService bidService,
                          final JwtTokenUtil jwtTokenUtil,
                          final SimpMessagingTemplate template,
                          final ProductController productController,
                          final UserService userService, ProductService productService,
                          final WatchlistService watchlistService) {
        Objects.requireNonNull(template, "template must not be null.");
        Objects.requireNonNull(jwtTokenUtil, "jwtTokenUtil must not be null.");
        Objects.requireNonNull(bidService, "bidService must not be null.");
        Objects.requireNonNull(productController, "productController must not be null.");
        Objects.requireNonNull(userService, "userService must not be null.");
        Objects.requireNonNull(productService, "productService must not be null.");
        Objects.requireNonNull(watchlistService, "watchlistService must not be null.");
        this.jwtTokenUtil = jwtTokenUtil;
        this.bidService = bidService;
        this.template = template;
        this.productController = productController;
        this.userService = userService;
        this.productService = productService;
        this.watchlistService = watchlistService;
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

}
