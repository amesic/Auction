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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @Column(length = 65535, columnDefinition = "text")
    private String description;
    private LocalDate datePublishing;

    @ManyToOne
    @Target(User.class)
    @JoinColumn(name = "idSeller")
    private User seller;

    private LocalDate startDate;
    private LocalDate endDate;
    private double startPrice;

    //bids table
    @OneToMany(mappedBy = "product", cascade={CascadeType.ALL})
    private List<Bid> bids = new ArrayList();

    //ImageProduct table
    @OneToMany(cascade={CascadeType.ALL}) //mapped by nece
    //@Target(ImageProduct.class)
    private List<Image> images = new ArrayList();

    @ManyToOne
    @Target(Category.class)
    @JoinColumn(name = "idCategory")
    private Category category;

    @ManyToOne
    @Target(Category.class)
    @JoinColumn(name = "idSubcategory")
    private Category subcategory;

    private Boolean feature;


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

    public LocalDate getDatePublishing() {
        return datePublishing;
    }

    public void setDatePublishing(LocalDate datePublishing) {
        this.datePublishing = datePublishing;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

   public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Category subcategory) {
        this.subcategory = subcategory;
    }

    public Boolean getFeature() {
        return feature;
    }

    public void setFeature(Boolean feature) {
        this.feature = feature;
    }
}