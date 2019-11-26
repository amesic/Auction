package com.ajla.auction.service;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.NumberOfProductsInfo;
import com.ajla.auction.model.Product;
import com.ajla.auction.repo.BidRepository;
import com.ajla.auction.repo.CategoryRepository;
import com.ajla.auction.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final BidRepository bidRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(final ProductRepository productRepository,
                          final BidRepository bidRepository,
                          final CategoryRepository categoryRepository) {
        Objects.requireNonNull(productRepository, "productRepository must not be null.");
        Objects.requireNonNull(bidRepository, "bidRepository must not be null.");
        Objects.requireNonNull(categoryRepository, "categoryRepository must not be null.");
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.bidRepository = bidRepository;
    }

    @Override
    public Product getAdvertisementProduct() {
        return productRepository.findProductById((long) 4);
    }
    @Override
    public List<Product> getFeatureProducts() {
        return productRepository.getAllFeatureProducts();
    }
    @Override
    public List<Product> getFeatureCollection() {
        return productRepository.getAllFeatureCollection();
    }
    @Override
    public PaginationInfo<Product> findPaginatedNewArrivals(final Long page, final Long size) {
        return productRepository.getAllNewArrivalProducts(page, size);
    }
    @Override
    public PaginationInfo<Product> findPaginatedLastChance(final Long page, final Long size) {
        return productRepository.getAllLastChanceProducts(page, size);
    }
    @Override
    public Product findSingleProduct (final Long id) {
        return productRepository.findProductById(id);
    }
    @Override
    public List<Product> getRelatedProducts (final Long idProduct) {
        return productRepository.getRelatedProducts(idProduct);
    }
    @Override
    public Boolean userIsSellerOfProduct(final Long idUser, final Long idProduct) {
        return productRepository.userIsSellerOfProduct(idUser, idProduct);
    }
    @Override
    public List<NumberOfProductsInfo> numberOfProductsBySubcategories() {
        return productRepository.numberOfProductsBySubcategory(categoryRepository.findAll());
    }
    @Override
    public PaginationInfo<Product> getAllProductsBySort(final String typeOfSort,
                                                        final Long subcategoryId,
                                                        final Long filterColorId,
                                                        final Long filterSizeId,
                                                        final Long pageNumber,
                                                        final Long size) {
        return productRepository.getAllProductsBySort(typeOfSort, subcategoryId, filterColorId, filterSizeId, pageNumber, size);
    }
}
