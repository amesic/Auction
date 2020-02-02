package com.ajla.auction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @JsonIgnore
    private LocalDateTime datePublishing;

    @ManyToOne
    @Target(User.class)
    @JoinColumn(name = "idSeller")
    @JsonIgnoreProperties({"password", "gender", "birthDate", "phoneNumber", "address"})
    private User seller;

    @JsonIgnore
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double startPrice;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Image> images = new ArrayList();

    @ManyToMany
    private List<Characteristic> characteristics = new ArrayList();

    @ManyToOne
    @Target(Category.class)
    @JoinColumn(name = "idCategory")
    @JsonIgnoreProperties({"name", "subcategories"})
    private Category category;

    @ManyToOne
    @Target(Category.class)
    @JoinColumn(name = "idSubcategory")
    @JsonIgnoreProperties({"name", "subcategories"})
    private Category subcategory;

    @JsonIgnore
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

    public LocalDateTime getDatePublishing() {
        return datePublishing;
    }

    public void setDatePublishing(LocalDateTime datePublishing) {
        this.datePublishing = datePublishing;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
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

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
