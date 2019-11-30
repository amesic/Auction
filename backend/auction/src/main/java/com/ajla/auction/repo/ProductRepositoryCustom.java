package com.ajla.auction.repo;

import com.ajla.auction.model.*;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getAllFeatureProducts();
    List<Product> getAllFeatureCollection();
    PaginationInfo<Product> getAllLastChanceProducts(final Long page, final Long size);
    PaginationInfo<Product> getAllNewArrivalProducts(final Long page, final Long size);
    Long getSubcategoryIdOfProduct(final Long idProduct);
    List<Product> getRelatedProducts(final Long idProduct);
    Boolean userIsSellerOfProduct(final Long idUser, final Long idProduct);
    List<NumberOfProductsInfo> numberOfProductsBySubcategory(final List<Category> categories);
    NumberOfProductsInfo numberOfProductsByCharacteristic(final Characteristic characteristic,
                                                          final Long subcategoryId,
                                                          final List<Long> listOfCharacteristicClicked);
    PriceProductInfo numberOfProductsByPrice(final Long subcategoryId, final List<Long> listOfCharacteristicsClicked);
    PaginationInfo<Product> getAllProductsBySort(final String typeOfSort,
                                                 final Long subcategoryId,
                                                 final Long filterColorId,
                                                 final Long filterSizeId,
                                                 final Double lowerBound,
                                                 final Double upperBound,
                                                 final Long pageNumber,
                                                 final Long size);
}
