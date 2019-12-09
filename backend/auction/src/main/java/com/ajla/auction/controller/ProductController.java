package com.ajla.auction.controller;

import com.ajla.auction.model.Product;
import com.ajla.auction.model.ProductViewers;
import com.ajla.auction.model.UserWatchProductId;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.PriceProductInfo;
import com.ajla.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final SimpMessagingTemplate template;

    List<ProductViewers> numberOfViewersByProduct = new ArrayList<>();
    List<UserWatchProductId> usersWatchingProduct = new ArrayList<>();

    @Autowired
    public ProductController(final ProductService productService,
                             final SimpMessagingTemplate template) {
        Objects.requireNonNull(productService, "productService must not be null.");
        Objects.requireNonNull(template, "template must not be null.");
        this.productService = productService;
        this.template = template;
    }

    @GetMapping("/featureProduct")
    public ResponseEntity<List<Product>> getFeatureProducts() {
        return new ResponseEntity<>(productService.getFeatureProducts(), HttpStatus.OK);
    }
    @GetMapping("/featureCollection")
    public ResponseEntity<List<Product>> getFeatureCollection() {
        return new ResponseEntity<>(productService.getFeatureCollection(), HttpStatus.OK);
    }
    @GetMapping("/advertisement")
    public ResponseEntity<Product> getAdvertisementProduct() {
        return new ResponseEntity<>(productService.getAdvertisementProduct(), HttpStatus.OK);
    }
    //http://localhost:8080/product/newArrivals?page=0&size=5
    @GetMapping("/newArrivals")
    public ResponseEntity<PaginationInfo<Product>> findPaginatedNewArrivals(@RequestParam("page") final Long page, @RequestParam("size") final Long size) {
       return new ResponseEntity<>(productService.findPaginatedNewArrivals(page, size), HttpStatus.OK);
    }
    @GetMapping("/lastChance")
    public ResponseEntity<PaginationInfo<Product>> findPaginatedLastChance(@RequestParam("page") final Long page, @RequestParam("size") final Long size) {
        return new ResponseEntity<>(productService.findPaginatedLastChance(page, size), HttpStatus.OK);
    }
    @GetMapping("/singleProduct")
    public ResponseEntity<Product> findSingleProduct(@RequestParam("id") final Long id) {
        return new ResponseEntity<>(productService.findSingleProduct(id), HttpStatus.OK);
    }
    @GetMapping("/numberViewers")
    public ResponseEntity<Long> getNumberViewersOfProduct(@RequestParam("id") final Long id) {
        if (this.numberOfViewersByProduct.size() == 0 ||
                this.numberOfViewersByProduct.stream().noneMatch(pv -> pv.getProductId().equals(id))) {
            return new ResponseEntity<>((long) 0, HttpStatus.OK);
        }
        return new ResponseEntity<>(this.numberOfViewersByProduct.stream()
                .filter(pv -> pv.getProductId().equals(id)).findFirst().get().getNumberOfCurrentViewers(), HttpStatus.OK);
    }
    @MessageMapping("/send/message/disconnect")
    @SendToUser
    public void onDisconnectMessage(final UserWatchProductId idEmail) {
        if (!idEmail.getEmail().equals("") && this.usersWatchingProduct.stream().anyMatch(uw -> uw.getSessionId().equals(idEmail.getSessionId()))) {
            this.usersWatchingProduct.removeIf(uw ->  uw.getSessionId().equals(idEmail.getSessionId()));
        }
        this.numberOfViewersByProduct.stream()
                .filter(pv -> pv.getProductId().equals(idEmail.getProductId())).findFirst().get().decrement();

        this.usersWatchingProduct.forEach(uw -> {
            if (uw.getProductId().equals(idEmail.getProductId())) {
                SimpMessageHeaderAccessor headerAcc = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                headerAcc.setSessionId(uw.getSessionId());
                headerAcc.setLeaveMutable(true);
                Long numberOfViewers = this.numberOfViewersByProduct.stream()
                        .filter(pv -> pv.getProductId().equals(idEmail.getProductId())).findFirst().get().getNumberOfCurrentViewers();
                numberOfViewers--;
                this.template.convertAndSendToUser(uw.getSessionId(), "/queue/notify", numberOfViewers, headerAcc.getMessageHeaders());
            }
        });

    }
    @MessageMapping("/send/message/connect")
    @SendToUser
    public void onConnectMessage(final UserWatchProductId idEmail) {
        if (!idEmail.getEmail().equals("")) {
            if (this.usersWatchingProduct.stream().noneMatch(uw -> uw.getSessionId().equals(idEmail.getSessionId()))) {
                this.usersWatchingProduct.add(idEmail);
            }
            List<ProductViewers> result = this.numberOfViewersByProduct.stream()
                    .filter(pv -> pv.getProductId().equals(idEmail.getProductId()))
                    .collect(Collectors.toList());

            if (result.isEmpty()) {
                // add productId for the first time
                ProductViewers productViewers = new ProductViewers();
                productViewers.setProductId(idEmail.getProductId());
                productViewers.increment();
                this.numberOfViewersByProduct.add(productViewers);
            } else {
                // Push notifications to front-end
                // product already exist, just increment number of views
                this.usersWatchingProduct.forEach(uw -> {
                    if (uw.getProductId().equals(idEmail.getProductId())) {
                        SimpMessageHeaderAccessor headerAcc = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                        headerAcc.setSessionId(uw.getSessionId());
                        headerAcc.setLeaveMutable(true);
                        this.template.convertAndSendToUser(uw.getSessionId(), "/queue/notify", this.numberOfViewersByProduct.stream()
                                .filter(pv -> pv.getProductId().equals(idEmail.getProductId())).findFirst().get().getNumberOfCurrentViewers(), headerAcc.getMessageHeaders());
                    }
                });
                this.numberOfViewersByProduct.stream()
                        .filter(pv -> pv.getProductId().equals(idEmail.getProductId())).findFirst().get().increment();
            }
        }

    }
    @GetMapping("/relatedProducts")
    public ResponseEntity<List<Product>> getRelatedProducts(@RequestParam("id") final Long idProduct) {
        return new ResponseEntity<>(productService.getRelatedProducts(idProduct), HttpStatus.OK);
    }
    @GetMapping("/sortProducts")
    public ResponseEntity<PaginationInfo<Product>> getAllProductsBySort(@RequestParam final String typeOfSort,
                                                                        @RequestParam(required = false) final Long subcategoryId,
                                                                        @RequestParam(required = false) final Long filterColorId,
                                                                        @RequestParam(required = false) final Long filterSizeId,
                                                                        @RequestParam(required = false) final Double lowerBound,
                                                                        @RequestParam(required = false) final Double upperBound,
                                                                        @RequestParam(required = false) final String searchUser,
                                                                        @RequestParam("pageNumber") final Long pageNumber,
                                                                        @RequestParam("size") final Long size) {
        return new ResponseEntity<>(productService.getAllProductsBySort(typeOfSort, subcategoryId,
                filterColorId, filterSizeId, lowerBound, upperBound, searchUser, pageNumber, size), HttpStatus.OK);
    }
    @GetMapping("/numberOfProductsByPrice")
    public ResponseEntity<PriceProductInfo> getAllProductsByPrice(@RequestParam(required = false) final Long subcategoryId,
                                                                  @RequestParam(required = false) final List<Long> listOfCharacteristicsClicked,
                                                                  @RequestParam(required = false) final String searchUser) {
        return new ResponseEntity<>(productService.getNumberProductsByPrice(subcategoryId, listOfCharacteristicsClicked, searchUser), HttpStatus.OK);
    }
}
