package com.ajla.auction.model;

import org.hibernate.annotations.Target;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.util.List;



@Entity
@Table(name = "characteristic")
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    private String name;

    @OneToMany(cascade = {CascadeType.ALL})
    @Target(Characteristic.class)
    @JoinColumn(name = "characteristicParentId")
    private List<Characteristic> allCharacteristic;

    @ManyToMany(mappedBy = "characteristics")
    private List<Product> products;

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

    public List<Characteristic> getAllCharacteristic() {
        return allCharacteristic;
    }

    public void setAllCharacteristic(List<Characteristic> allCharacteristic) {
        this.allCharacteristic = allCharacteristic;
    }
}
