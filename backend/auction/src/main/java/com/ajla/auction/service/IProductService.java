package com.ajla.auction.service;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.NumberOfProductsInfo;
import com.ajla.auction.model.PriceProductInfo;
import com.ajla.auction.model.Product;
import com.ajla.auction.model.ProductInfoBid;

import java.util.List;

public interface IProductService {
    Product getAdvertisementProduct();
    List<Product> getFeatureProducts();
    List<Product> getFeatureCollection();
    PaginationInfo<Product> findPaginatedNewArrivals(final Long page, final Long size);
    PaginationInfo<Product> findPaginatedLastChance(final Long page, final Long size);
    Product findSingleProduct(final Long id);
    List<Product> getRelatedProducts(final Long idProduct);
    Boolean userIsSellerOfProduct(final Long idUser, final Long idProduct);
    List<NumberOfProductsInfo> numberOfProductsBySubcategories();
    PaginationInfo<Product> getAllProductsBySort(final String typeOfSort,
                                                 final Long subcategoryId,
                                                 final Long filterColorId,
                                                 final Long filterSizeId,
                                                 final Double lowerBound,
                                                 final Double upperBound,
                                                 final String searchUser,
                                                 final Long pageNumber,
                                                 final Long size);
    PriceProductInfo getNumberProductsByPrice(final Long subcategoryId,
                                              final List<Long> listOfCharacteristicsClicked,
                                              final String searchUser);
    PaginationInfo<ProductInfoBid> getAllActiveProductsOfSeller(final String email, final Long page, final Long size);
    PaginationInfo<ProductInfoBid> getAllSoldProductsOfSeller(final String email, final Long page, final Long size);
}
