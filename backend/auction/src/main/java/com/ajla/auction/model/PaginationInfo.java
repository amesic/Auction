package com.ajla.auction.model;

import java.util.List;

public class PaginationInfo<Type> {
    private Long pageSize;
    private Long pageNumber;
    private Long totalNumberOfItems;
    private List<Type> items;

    public PaginationInfo(Long pageSize, Long pageNumber, Long totalNumberOfItems, List<Type> items) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalNumberOfItems = totalNumberOfItems;
        this.items = items;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getTotalNumberOfItems() {
        return totalNumberOfItems;
    }

    public void setTotalNumberOfItems(Long totalNumberOfItems) {
        this.totalNumberOfItems = totalNumberOfItems;
    }

    public List<Type> getItems() {
        return items;
    }

    public void setItems(List<Type> items) {
        this.items = items;
    }
}
