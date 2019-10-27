package com.ajla.auction.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    private Long id;

    private String street;
    private String  city;
    private String zipCode;
    private String country;

    @OneToOne(mappedBy = "address")
    private User user;
}
