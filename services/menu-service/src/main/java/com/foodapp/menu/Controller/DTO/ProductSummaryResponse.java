package com.foodapp.menu.Controller.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductSummaryResponse {

    private String sku;

    private String title;

    private String description;

    private BigDecimal price;

    private List<String> tags;

    private Integer quantity;

    private Boolean available;
}
