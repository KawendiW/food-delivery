package com.foodapp.menu.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document("products")
@CompoundIndex(name = "shop_sku_uq", def = "{'shopId':1,'sku':1}", unique = true)
@CompoundIndex(name = "shop_tags_idx", def = "{'shopId':1,'tags':1}")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    private String id;

    @Indexed
    private String shopId;

    private String sku;

    private String title;

    private String description;

    private BigDecimal price;

    private List<String> tags;

    private Integer quantity;

    private Boolean available;

    @Version
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
