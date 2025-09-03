package com.foodapp.menu.MapStruct;

import com.foodapp.menu.Controller.DTO.*;
import com.foodapp.menu.Entity.ProductEntity;
import com.foodapp.menu.Entity.ShopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ProductMapper {

    ProductEntity toEntity(ProductCreateRequest request);

    ProductSummaryResponse toSummary(ProductEntity entity);

    List<ProductSummaryResponse> toSummaryList(List<ProductEntity> entities);

    void update(@MappingTarget ProductEntity entity, ProductUpdateRequest request);

}
