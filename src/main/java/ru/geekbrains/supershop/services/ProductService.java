package ru.geekbrains.supershop.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.geekbrains.supershop.exceptions.EntityNotFoundException;
import ru.geekbrains.supershop.persistence.entities.Country;
import ru.geekbrains.supershop.persistence.entities.Image;
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.persistence.entities.enums.ProductCategory;
import ru.geekbrains.supershop.persistence.pojo.ProductPojo;

import ru.geekbrains.supershop.persistence.repositories.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    public Product findOneById(UUID uuid) throws EntityNotFoundException {
        return productRepository.findById(uuid).orElseThrow(
            () -> new EntityNotFoundException("Oops! Product " + uuid + " wasn't found!")
        );
    }

//    public List<Product> findAll(Integer category) {
//        return category == null ? productRepository.findAll() : productRepository.findAllByCategory(ProductCategory.values()[category]);
//    }

    public List<Product> findAll(Integer category) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        //список того, чего хотим получить
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        //корневая сущность к которой джойним или фильтруем
        Root<Product> root = criteriaQuery.from(Product.class);

        if (category == null) {
            CriteriaQuery<Product> select = criteriaQuery.select(root);
            return entityManager.createQuery(select).getResultList();
        }

        //параметры фильтра
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("category"), category));

        //добавление параметров в запрос
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public List<Product> findAllByCountry(Integer country) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> root = criteriaQuery.from(Product.class);
        Join<Product, Country> productCountryJoin = root.join("country");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(productCountryJoin.get("country"), country));

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }


    @Transactional
    public String save(ProductPojo productPogo, Image image) {

        Product product = Product.builder()
            .added(new Date())
            .title(productPogo.getTitle())
            .description(productPogo.getDescription())
            .price(productPogo.getPrice())
            .available(productPogo.isAvailable())
            .category(productPogo.getCategory())
            .image(image)
        .build();

        productRepository.save(product);
        log.info("New Product has been succesfully added! {}", product);
        return "redirect:/";
    }

}