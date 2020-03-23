package ru.mystore.store.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "product_images")
public class Image {

    // private static final long SUID = 1L;

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String path;

}