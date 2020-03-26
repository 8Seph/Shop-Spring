package ru.mystore.store.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mystore.store.persistence.entities.Shopuser;

import java.util.UUID;

@Repository
public interface ShopuserRepository extends CrudRepository<Shopuser, UUID> {
    Shopuser findOneByPhone(String phone);
    boolean existsByPhone(String phone);
}