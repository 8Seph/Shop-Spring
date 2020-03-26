package ru.geekbrains.supershop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< Updated upstream:src/main/java/ru/mystore/store/persistence/repositories/ProductRepository.java
import ru.mystore.store.persistence.entities.Product;
import ru.mystore.store.persistence.entities.enums.ProductCategory;
=======
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.persistence.entities.enums.ProductCategory;
>>>>>>> Stashed changes:src/main/java/ru/geekbrains/supershop/persistence/repositories/ProductRepository.java

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAll();
    List<Product> findAllByCategory(ProductCategory category);
}