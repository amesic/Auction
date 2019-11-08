package com.ajla.auction.model;

import java.lang.reflect.Array;
import java.util.List;

public class PaginationInfo<Type> {
    private long pageSize;
    private long pageNumber;
    private long totalNumberOfItems;
    private List<Type> items;

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalNumberOfItems() {
        return totalNumberOfItems;
    }

    public void setTotalNumberOfItems(long totalNumberOfItems) {
        this.totalNumberOfItems = totalNumberOfItems;
    }

    public List<Type> getItems() {
        return items;
    }

    public void setItems(List<Type> items) {
        this.items = items;
    }
}
