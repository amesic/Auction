package com.ajla.auction.model;

import org.hibernate.annotations.Target;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bids")
public class Bid {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Product product;

    private Number value;
    private Date date;
}

