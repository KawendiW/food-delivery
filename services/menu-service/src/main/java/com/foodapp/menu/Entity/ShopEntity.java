package com.foodapp.menu.Entity;

import com.foodapp.menu.Entity.embedded.ShopAddress;
import com.foodapp.menu.Entity.embedded.ShopWorkingHours;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document("shops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String slug;

    private String name;

    private String description;

    private List<ShopAddress> addresses;

    private ShopWorkingHours workingHours;

    private Boolean available;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

}
