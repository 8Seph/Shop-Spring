package ru.mystore.store.persistence.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import ru.mystore.store.persistence.entities.enums.Role;
import ru.mystore.store.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "shopuser")
@EqualsAndHashCode(callSuper = true)
public class Shopuser extends PersistableEntity {
    private String phone;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "shopuser")
    private List<Purchase> purchases;

}