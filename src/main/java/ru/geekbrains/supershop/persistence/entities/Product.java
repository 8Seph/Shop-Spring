package ru.geekbrains.supershop.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.NoArgsConstructor;
<<<<<<< Updated upstream:src/main/java/ru/mystore/store/persistence/entities/Product.java
import ru.mystore.store.persistence.entities.enums.ProductCategory;
import ru.mystore.store.persistence.entities.utils.PersistableEntity;
=======
import ru.geekbrains.supershop.persistence.entities.enums.ProductCategory;
import ru.geekbrains.supershop.persistence.entities.utils.PersistableEntity;
>>>>>>> Stashed changes:src/main/java/ru/geekbrains/supershop/persistence/entities/Product.java

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends PersistableEntity {

    private String title;

    private String description;

    private Date added;

    private Double price;

    private boolean available;

    @Enumerated(EnumType.ORDINAL)
    private ProductCategory category;

    @OneToOne
    @JoinColumn(name = "image")
    private Image image;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;
}