package com.foodapp.menu.Repository;


import com.foodapp.menu.Entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    Page<ProductEntity> findByShopId(String shopId, Pageable pageable);

    Optional<ProductEntity> findByShopIdAndSku(String shopId, String sku);

    boolean existsBySku(String s);

    void deleteByShopIdAndSku(String shopId, String sku);
}
