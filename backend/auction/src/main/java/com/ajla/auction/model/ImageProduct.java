package com.ajla.auction.model;

import org.hibernate.annotations.Target;

import javax.persistence.*;

@Entity
@Table(name = "imagesProduct")
public class ImageProduct {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    private Product product;

    private String link; //path for image

}
