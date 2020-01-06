package com.ajla.auction.model;

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



@Entity
@Table(name = "rates")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @Target(User.class)
    @JoinColumn(name = "idUser")
    @JsonIgnoreProperties({"password", "gender", "birthDate", "phoneNumber", "address"})
    private User user;

    @ManyToOne
    @Target(User.class)
    @JoinColumn(name = "idSeller")
    @JsonIgnoreProperties({"password", "gender", "birthDate", "phoneNumber", "address"})
    private User seller;

    private int value;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
