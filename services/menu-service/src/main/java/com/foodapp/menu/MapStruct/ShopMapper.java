package com.foodapp.menu.MapStruct;

import com.foodapp.menu.Controller.DTO.ShopCreateRequest;
import com.foodapp.menu.Controller.DTO.ShopSummaryResponse;
import com.foodapp.menu.Controller.DTO.ShopUpdateRequest;
import com.foodapp.menu.Entity.ShopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ShopMapper {

    ShopEntity toEntity(ShopCreateRequest request);

    ShopSummaryResponse toSummary(ShopEntity entity);

    List<ShopSummaryResponse> toSummaryList(List<ShopEntity> entities);

    void update(@MappingTarget ShopEntity entity, ShopUpdateRequest request);

}
