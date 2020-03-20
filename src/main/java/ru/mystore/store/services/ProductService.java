package ru.mystore.store.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mystore.store.exceptions.ProductNotFoundException;
import ru.mystore.store.persistence.entities.Product;
import ru.mystore.store.persistence.entities.enums.ProductCategory;
import ru.mystore.store.persistence.repositories.ProductRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public boolean checkProductUUID(String id) {
        List<Product> products = productRepository.findAll();
        for (Product p : products) {
            if (p.getId().toString().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Product findOneById(UUID uuid) throws ProductNotFoundException {
        return productRepository.findById(uuid).orElseThrow(
                () -> new ProductNotFoundException("Oops! Product " + uuid + " wasn't found!")
        );
    }


    public List<Product> findAll(Integer category) {
        return category == null ? productRepository.findAll() : productRepository.findAllByCategory(ProductCategory.values()[category]);
    }

    public List<Product> findAllOrderByAvailableDesc(){
        return productRepository.findAllOrderByAvailableDesc();
    }

}