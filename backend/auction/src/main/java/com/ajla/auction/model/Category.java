package com.ajla.auction.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    private Long id;

    private String name;

    //međutabela product_category, mappedBy ime je ime varijable iz product klase
    @ManyToMany(mappedBy = "productCategories")
    List<Product> products;
}
