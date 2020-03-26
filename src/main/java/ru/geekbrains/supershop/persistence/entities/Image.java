package ru.geekbrains.supershop.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

<<<<<<< Updated upstream:src/main/java/ru/mystore/store/persistence/entities/Image.java
import ru.mystore.store.persistence.entities.utils.PersistableEntity;
=======
import ru.geekbrains.supershop.persistence.entities.utils.PersistableEntity;
>>>>>>> Stashed changes:src/main/java/ru/geekbrains/supershop/persistence/entities/Image.java

import javax.persistence.Entity;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Image extends PersistableEntity implements Serializable {
    private static final long SUID = 1L;
    private String name;
}