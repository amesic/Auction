package com.ajla.auction.model;

import org.hibernate.annotations.Target;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
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

    //tabela bids
    @OneToMany(mappedBy = "product")
    private List<Bid> bids= new ArrayList();

    //tabela ImageProduct
    @OneToMany(mappedBy = "product")
    private List<Bid> images= new ArrayList();

    //međutabela product_category
    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = { @JoinColumn(name = "idProduct") },
            inverseJoinColumns = { @JoinColumn(name = "idCategory") }
    )
    List<Category> productCategories = new ArrayList();
    //List<Category> subcategories;


}
