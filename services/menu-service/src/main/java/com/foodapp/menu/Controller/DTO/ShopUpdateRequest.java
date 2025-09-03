package com.foodapp.menu.Controller.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.foodapp.menu.Entity.embedded.ShopAddress;
import com.foodapp.menu.Entity.embedded.ShopWorkingHours;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopUpdateRequest {

    private String name;

    private String description;

    private List<ShopAddress> addresses;

    private ShopWorkingHours workingHours;

    private Boolean available;
}
