package com.foodapp.menu.Fixtures;

import com.foodapp.menu.Controller.DTO.ShopCreateRequest;
import com.foodapp.menu.Controller.DTO.ShopSummaryResponse;
import com.foodapp.menu.Controller.DTO.ShopUpdateRequest;
import com.foodapp.menu.Entity.ShopEntity;
import com.foodapp.menu.Entity.embedded.DaySchedule;
import com.foodapp.menu.Entity.embedded.ShopAddress;
import com.foodapp.menu.Entity.embedded.ShopWorkingHours;
import com.foodapp.menu.Entity.embedded.TimeRange;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public final class ShopMothers {

    @Value
    @Builder(toBuilder = true)
    public static class ShopCreateRequestBuilder {
        @Builder.Default
        String name = "name";
        @Builder.Default
        String description = "description";
        @Builder.Default
        List<ShopAddress> addresses = new ArrayList<>(List.of(
                new ShopAddress("City2", "Street2", "House2"),
                new ShopAddress("City1", "Street1", "House1")
        ));
        @Builder.Default
        ShopWorkingHours workingHours = new ShopWorkingHours(new ArrayList<>(List.of(
                new DaySchedule("TUE", new ArrayList<>(List.of(new TimeRange("09:00", "21:00")))),
                new DaySchedule("MON", new ArrayList<>(List.of(new TimeRange("10:00", "20:00"))))
        )));

        public ShopCreateRequest build() {
            ShopCreateRequest request = new ShopCreateRequest();
            request.setName(name);
            request.setDescription(description);
            request.setAddresses(new ArrayList<>(addresses));
            request.setWorkingHours(workingHours);
            return request;
        }
    }

    public static ShopCreateRequestBuilder shopCreateRequestBuilder() {
        return ShopCreateRequestBuilder.builder().build();
    }

    @Value
    @Builder(toBuilder = true)
    public static class ShopUpdateRequestBuilder {
        @Builder.Default
        String name = "new name";
        @Builder.Default
        String description = "new description";
        @Builder.Default
        List<ShopAddress> addresses = new ArrayList<>(List.of(
                new ShopAddress("new City2", "new Street2", "new House2"),
                new ShopAddress("new City1", "new Street1", "new House1")
        ));
        @Builder.Default
        ShopWorkingHours workingHours = new ShopWorkingHours(new ArrayList<>(List.of(
                new DaySchedule("THU", new ArrayList<>(List.of(new TimeRange("11:00", "22:00")))),
                new DaySchedule("FRI", new ArrayList<>(List.of(new TimeRange("08:00", "18:00"))))
        )));
        @Builder.Default
        Boolean available = true;

        public ShopUpdateRequest build() {
            ShopUpdateRequest request = new ShopUpdateRequest();
            request.setName(name);
            request.setDescription(description);
            request.setAddresses(new ArrayList<>(addresses));
            request.setWorkingHours(workingHours);
            request.setAvailable(available);
            return request;
        }

    }

    public static ShopUpdateRequestBuilder shopUpdateRequestBuilder() {
        return ShopUpdateRequestBuilder.builder().build();
    }

    @Value
    @Builder(toBuilder = true)
    public static class ShopSummaryResponseBuilder{
        @Builder.Default
        String slug = "shop-slug";
        @Builder.Default
        String name = "default name";
        @Builder.Default
        String description = "default description";
        @Builder.Default
        List<ShopAddress> addresses = new ArrayList<>(List.of(
                new ShopAddress("default City2", "default Street2", "default House2"),
                new ShopAddress("default City1", "default Street1", "default House1")
        ));
        @Builder.Default
        ShopWorkingHours workingHours = new ShopWorkingHours(new ArrayList<>(List.of(
                new DaySchedule("TUE", new ArrayList<>(List.of(new TimeRange("09:00", "21:00")))),
                new DaySchedule("MON", new ArrayList<>(List.of(new TimeRange("10:00", "20:00"))))
        )));

        public ShopSummaryResponse build() {
            ShopSummaryResponse response = new ShopSummaryResponse();
            response.setName(name);
            response.setDescription(description);
            response.setAddresses(new ArrayList<>(addresses));
            response.setWorkingHours(workingHours);
            return response;
        }
    }

    public static ShopSummaryResponseBuilder shopSummaryResponseBuilder() {
        return ShopSummaryResponseBuilder.builder().build();
    }

    @Value
    @Builder(toBuilder = true)
    public static class ShopEntityBuilder {
        @Builder.Default
        String id = "shop1";
        @Builder.Default
        String slug = "shop-slug";
        @Builder.Default
        String name = "default name";
        @Builder.Default
        String description = "default description";
        @Builder.Default
        List<ShopAddress> addresses = new ArrayList<>(List.of(
                new ShopAddress("default City2", "default Street2", "default House2"),
                new ShopAddress("default City1", "default Street1", "default House1")
        ));
        @Builder.Default
        ShopWorkingHours workingHours = new ShopWorkingHours(new ArrayList<>(List.of(
                new DaySchedule("TUE", new ArrayList<>(List.of(new TimeRange("09:00", "21:00")))),
                new DaySchedule("MON", new ArrayList<>(List.of(new TimeRange("10:00", "20:00"))))
        )));
        @Builder.Default
        Boolean available = false;

        public ShopEntity build() {
            ShopEntity entity = new ShopEntity();
            entity.setId(id);
            entity.setSlug(slug);
            entity.setName(name);
            entity.setDescription(description);
            entity.setAddresses(new ArrayList<>(addresses));
            entity.setWorkingHours(workingHours);
            entity.setAvailable(available);
            return entity;
        }
    }

    public static ShopEntityBuilder shopEntityBuilder() {
        return ShopEntityBuilder.builder().build();
    }

}