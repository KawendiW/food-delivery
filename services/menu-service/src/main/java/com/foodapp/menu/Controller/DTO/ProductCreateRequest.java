package com.foodapp.menu.Controller.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductCreateRequest {

    private String title;

    private String description;

    private BigDecimal price;

    private List<String> tags;

    private Integer quantity;

}
