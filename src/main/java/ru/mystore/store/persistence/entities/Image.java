package ru.mystore.store.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import ru.mystore.store.persistence.entities.utils.PersistableEntity;

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