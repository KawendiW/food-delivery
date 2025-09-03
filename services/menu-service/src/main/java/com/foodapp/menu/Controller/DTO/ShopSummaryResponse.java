package com.foodapp.menu.Controller.DTO;

import com.foodapp.menu.Entity.embedded.ShopAddress;
import com.foodapp.menu.Entity.embedded.ShopWorkingHours;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopSummaryResponse {

    private String slug;

    private String name;

    private String description;

    private List<ShopAddress> addresses;

    private ShopWorkingHours workingHours;
}
