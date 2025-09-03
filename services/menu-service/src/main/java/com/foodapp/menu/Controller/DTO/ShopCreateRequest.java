package com.foodapp.menu.Controller.DTO;

import com.foodapp.menu.Entity.embedded.ShopAddress;
import com.foodapp.menu.Entity.embedded.ShopWorkingHours;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopCreateRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private List<ShopAddress> addresses;

    @NotNull
    private ShopWorkingHours workingHours;
}
