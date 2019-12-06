package com.ajla.auction.controller;

import com.ajla.auction.model.*;
import com.ajla.auction.service.ProductService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://atlantbh-auction.herokuapp.com"}, allowCredentials = "true")
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private List<ProductViewers> numberOfViewersByProduct = new ArrayList<>();
    private Map<String, String> userSessionId = new HashMap<>();

    private final SimpMessagingTemplate template;
    private final SimpUserRegistry simpUserRegistry;


    @Autowired
    public ProductController(final ProductService productService,
                             final SimpMessagingTemplate template,
                             final SimpUserRegistry simpUserRegistry) {
        Objects.requireNonNull(productService, "productService must not be null.");
        Objects.requireNonNull(template, "template must not be null.");
        this.productService = productService;
        this.template = template;
        this.simpUserRegistry = simpUserRegistry;
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
                !this.numberOfViewersByProduct.stream().filter(pv -> pv.getProductId().equals(id)).findFirst().isPresent()) {
            return new ResponseEntity<>((long) 0, HttpStatus.OK);
        }
        return new ResponseEntity<>(this.numberOfViewersByProduct.stream()
                .filter(pv -> pv.getProductId().equals(id)).findFirst().get().getNumberOfCurrentViewers(), HttpStatus.OK);
    }
    @MessageMapping("/send/message/disconnect")
    public void onDisconnectMessage(final UserWatchProductId idEmail, final SimpMessageHeaderAccessor headerAccessor) throws JSONException {
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        headerAccessor.setSessionId(sessionId);
        if (!idEmail.getEmail().equals("") && userSessionId.containsKey(idEmail.getEmail())) {
            userSessionId.remove(idEmail.getEmail(), sessionId);
        }
        this.numberOfViewersByProduct.stream()
                .filter(pv -> pv.getProductId().equals(idEmail.getProductId())).findFirst().get().decrement();
        this.template.convertAndSend("/topic/view", this.numberOfViewersByProduct.stream()
                .filter(pv -> pv.getProductId().equals(idEmail.getProductId())).findFirst().get());
    }
    @MessageMapping("/send/message/connect")
    public void onConnectMessage(final UserWatchProductId idEmail, final SimpMessageHeaderAccessor headerAccessor) {
        if (!idEmail.getEmail().equals("")) {
            String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
            headerAccessor.setSessionId(sessionId);
            if (!userSessionId.containsKey(idEmail.getEmail())) {
                userSessionId.put(idEmail.getEmail(), sessionId);
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
                // Push notifications to front-end
                this.template.convertAndSend("/topic/view", productViewers);
            } else {
                // product already exist, just increment number of views
                this.numberOfViewersByProduct.stream()
                        .filter(pv -> pv.getProductId().equals(idEmail.getProductId())).findFirst().get().increment();
                // Push notifications to front-end
                this.template.convertAndSend("/topic/view", this.numberOfViewersByProduct.stream()
                        .filter(pv -> pv.getProductId().equals(idEmail.getProductId())).findFirst().get());
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
