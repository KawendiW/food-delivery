package com.foodapp.menu.Entity.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ShopWorkingHours {
    private List<DaySchedule> days;
}

