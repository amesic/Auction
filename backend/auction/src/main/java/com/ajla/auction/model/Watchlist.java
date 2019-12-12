package com.ajla.auction.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Basic;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "watchlist")
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties({"password", "gender", "birthDate", "phoneNumber", "address"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    @JsonIgnoreProperties({"description", "datePublishing", "seller", "startDate", "startPrice", "category", "subcategory", "feature"})
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
