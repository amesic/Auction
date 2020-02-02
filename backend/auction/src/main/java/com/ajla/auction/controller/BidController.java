package com.ajla.auction.controller;

import com.ajla.auction.config.JwtTokenUtil;
import com.ajla.auction.model.Bid;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.service.BidService;
import com.ajla.auction.service.ProductService;
import com.ajla.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/bid")
public class BidController {
    private final BidService bidService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public BidController(final BidService bidService,
                          final JwtTokenUtil jwtTokenUtil,
                          final ProductService productService,
                          final UserService userService) {
        Objects.requireNonNull(bidService, "bidService must not be null.");
        Objects.requireNonNull(jwtTokenUtil, "jwtTokenUtil must not be null.");
        Objects.requireNonNull(productService, "productService must not be null.");
        Objects.requireNonNull(userService, "userService must not be null.");
        this.bidService = bidService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/bidsOfProduct")
    public ResponseEntity<PaginationInfo<Bid>> getBidsOfProduct (@RequestParam("pageNumber") final Long pageNumber,
                                                     @RequestParam("size") final Long size,
                                                     @RequestParam("id") final Long idProduct,
                                                     final HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        if(!requestTokenHeader.equals("")) {
            jwtToken = requestTokenHeader.substring(7);
            if (!productService.userIsSellerOfProduct(
                    userService.findByEmail(jwtTokenUtil.getUsernameFromToken(jwtToken)).getId(),
                    idProduct)
            ) {
                final PaginationInfo<Bid> bidInfoOfUser = bidService.bidsOfProduct(pageNumber, size, idProduct);
                if(bidInfoOfUser != null) {
                    bidInfoOfUser.setItems(Collections.singletonList(bidInfoOfUser.getItems().get(0)));
                }
                return new ResponseEntity<>(bidInfoOfUser, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(bidService.bidsOfProduct(pageNumber, size, idProduct), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
