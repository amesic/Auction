package com.ajla.auction.model;

import org.hibernate.annotations.Target;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    private String title;
    private String description;
    private Number totalProducts;
    private Date datePublishing;

    @ManyToOne
    @Target(User.class)
    @JoinColumn(name = "idSeller", referencedColumnName = "id")
    private User seller;

    private Date startDate;
    private Date endDate;
    private Number startPrice;

    //bids table
    @OneToMany(mappedBy = "product")
    private List<Bid> bids= new ArrayList();

    //ImageProduct table
    @OneToMany(mappedBy = "product")
    private List<Bid> images= new ArrayList();

    //inter-table product_category
    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = { @JoinColumn(name = "idProduct") },
            inverseJoinColumns = { @JoinColumn(name = "idCategory") }
    )
    private List<Category> productCategories = new ArrayList();
    //List<Category> subcategories;

    //getter setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Number getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Number totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Date getDatePublishing() {
        return datePublishing;
    }

    public void setDatePublishing(Date datePublishing) {
        this.datePublishing = datePublishing;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Number getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Number startPrice) {
        this.startPrice = startPrice;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<Bid> getImages() {
        return images;
    }

    public void setImages(List<Bid> images) {
        this.images = images;
    }

    public List<Category> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<Category> productCategories) {
        this.productCategories = productCategories;
    }
}
