package ru.mystore.store.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.mystore.store.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;
import java.io.Serializable;

//Запись в корзине товаров

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "cart_record")
public class CartRecord extends PersistableEntity {

    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "purchase")
    private Purchase purchase;


    public CartRecord(Product product) {
        this.product = product;
        this.quantity = 1;
        this.price = product.getPrice();
    }

}
