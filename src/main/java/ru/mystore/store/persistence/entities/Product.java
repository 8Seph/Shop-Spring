package ru.mystore.store.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import ru.mystore.store.persistence.entities.enums.ProductCategory;



import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product  {

    @Id
    @Column(name = "product_id")
    private UUID id;

    private String title;

    private String description;

    private Date added;

    private Double price;

    private boolean available;

    @Enumerated(EnumType.ORDINAL)
    private ProductCategory category;

    @OneToMany(mappedBy = "product") //название сущности внутри image
    private List<Image> images;

}