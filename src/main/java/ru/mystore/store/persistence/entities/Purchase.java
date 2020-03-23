package ru.mystore.store.persistence.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Purchase {

    @Id
    private UUID ud;
    private String address;
    private String phone;
}
