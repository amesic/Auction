package com.ajla.auction.model;

import java.util.List;

public class NumberOfProductsInfo {
    private Long id;
    private String name;
    private Long numberOfProducts;
    private List<NumberOfProductsInfo> children;

    public NumberOfProductsInfo(Long id, String name, List<NumberOfProductsInfo> subcategories, Long numberOfProducts) {
        this.id = id;
        this.name = name;
        this.children = subcategories;
        this.numberOfProducts = numberOfProducts;
    }

    public Long getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(Long numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NumberOfProductsInfo> getChildren() {
        return children;
    }

    public void setChildren(List<NumberOfProductsInfo> children) {
        this.children = children;
    }
}
