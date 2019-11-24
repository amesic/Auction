package com.ajla.auction.model;

import org.hibernate.annotations.Target;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    private String name;
    //cascade bc you have a collection in your entity,
    //and that collection has one or more items which are not present in the database.
    //By specifying the above options you tell hibernate to save them to the database when saving their parent
    //or else org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing: com.ajla.auction.model.Category
    @OneToMany(cascade = CascadeType.ALL)
    @Target(Category.class)
    @JoinColumn(name = "categoryParentId")
    private List<Category> subcategories= new ArrayList();

    @ManyToMany
    private List<Characteristic> characteristics = new ArrayList();

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

    public List<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Category> subcategories) {
        this.subcategories = subcategories;
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
}
