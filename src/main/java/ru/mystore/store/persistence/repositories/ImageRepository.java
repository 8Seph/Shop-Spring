package ru.mystore.store.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mystore.store.persistence.entities.Image;


import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
//    @Query(value = "SELECT image.name FROM image INNER JOIN product p ON image.id = p.image WHERE p.id = :id", nativeQuery = true)
//    String obtainImageNameByProductId(@Param("id") UUID id);

    @Query(value = "SELECT i.path FROM product_images i INNER JOIN product p ON i.product_id = p.product_id WHERE p.product_id = :id LIMIT 1", nativeQuery = true)
    String getFirstImageByProductId(@Param("id") UUID id);

    @Query(value = "SELECT i.path FROM product_images i INNER JOIN product p ON i.product_id = p.product_id WHERE p.product_id = :id", nativeQuery = true)
    List<String> getAllImagesByProductId(@Param("id") UUID id);

}