package com.foodapp.menu.Repository;


import com.foodapp.menu.Entity.ShopEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends MongoRepository<ShopEntity, String> {

    Optional<ShopEntity> findBySlug(String slug);

    boolean existsBySlug(String slug);

    void deleteBySlug(String slug);
}
