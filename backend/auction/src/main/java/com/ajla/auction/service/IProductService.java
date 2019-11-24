package com.ajla.auction.service;

import com.ajla.auction.model.PaginationInfo;
import com.ajla.auction.model.NumberOfProductsInfo;
import com.ajla.auction.model.Product;

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
}
