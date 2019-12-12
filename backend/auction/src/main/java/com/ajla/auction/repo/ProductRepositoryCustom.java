package com.ajla.auction.repo;

import com.ajla.auction.model.Product;
import com.ajla.auction.model.ProductInfoBid;
import com.ajla.auction.model.Category;
import com.ajla.auction.model.PriceProductInfo;
import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.Characteristic;
import com.ajla.auction.model.NumberOfProductsInfo;

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
                                                          final List<Long> listOfCharacteristicClicked,
                                                          final String searchUser,
                                                          final Double lowerBound,
                                                          final Double upperBound);
    PriceProductInfo numberOfProductsByPrice(final Long subcategoryId,
                                             final List<Long> listOfCharacteristicsClicked,
                                             final String searchUser);
    PaginationInfo<Product> getAllProductsBySort(final String typeOfSort,
                                                 final Long subcategoryId,
                                                 final Long filterColorId,
                                                 final Long filterSizeId,
                                                 final Double lowerBound,
                                                 final Double upperBound,
                                                 final String searchUser,
                                                 final Long pageNumber,
                                                 final Long size);
    PaginationInfo<ProductInfoBid> getAllActiveProductsOfSeller(final Long idSeller, final Long page, final Long size);
    PaginationInfo<ProductInfoBid> getAllSoldProductsOfSeller(final Long idSeller, final Long page, final Long size);
}
