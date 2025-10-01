package com.foodapp.menu.Fixtures;

import com.foodapp.menu.Controller.DTO.ProductCreateRequest;
import com.foodapp.menu.Controller.DTO.ProductSummaryResponse;
import com.foodapp.menu.Controller.DTO.ProductUpdateRequest;
import com.foodapp.menu.Entity.ProductEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public final class ProductMother {

    @Value
    @Builder(toBuilder = true)
    public static class ProductCreateRequestBuilder {
        @Builder.Default
        String title = "title";
        @Builder.Default
        String description = "description";
        @Builder.Default
        BigDecimal price = BigDecimal.valueOf(200);
        @Builder.Default
        Integer quantity = 100;
        @Builder.Default
        List<String> tags = new ArrayList<>(List.of("Tags1", "Tags2", "Tags3"));

        public ProductCreateRequest build() {
            ProductCreateRequest request = new ProductCreateRequest();
            request.setTitle(title);
            request.setDescription(description);
            request.setPrice(price);
            request.setQuantity(quantity);
            request.setTags(new ArrayList<>(tags));
            return request;
        }
    }

    public static ProductCreateRequestBuilder productCreateRequestBuilder() {
        return ProductCreateRequestBuilder.builder().build();
    }

    @Value
    @Builder(toBuilder = true)
    public static class ProductUpdateRequestBuilder {
        @Builder.Default
        String title = "new title";
        @Builder.Default
        String description = "new description";
        @Builder.Default
        BigDecimal price = BigDecimal.valueOf(300);
        @Builder.Default
        Integer quantity = 200;
        @Builder.Default
        List<String> tags = new ArrayList<>(List.of("new Tags1", "new Tags2", "new Tags3"));

        public ProductUpdateRequest build() {
            ProductUpdateRequest request = new ProductUpdateRequest();
            request.setTitle(title);
            request.setDescription(description);
            request.setPrice(price);
            request.setQuantity(quantity);
            request.setTags(new ArrayList<>(tags));
            return request;
        }
    }

    public static ProductUpdateRequestBuilder productUpdateRequestBuildr() {
        return ProductUpdateRequestBuilder.builder().build();
    }

    @Value
    @Builder(toBuilder = true)
    public static class ProductSummaryResponseBuilder{
        @Builder.Default
        String sku = "product-sku";
        @Builder.Default
        String title = "title";
        @Builder.Default
        String description = "description";
        @Builder.Default
        BigDecimal price = BigDecimal.valueOf(200);
        @Builder.Default
        Integer quantity = 100;
        @Builder.Default
        List<String> tags = new ArrayList<>(List.of("Tags1", "Tags2", "Tags3"));
        @Builder.Default
        Boolean available = false;

        public ProductSummaryResponse build(){
            ProductSummaryResponse response = new ProductSummaryResponse();
            response.setSku(sku);
            response.setTitle(title);
            response.setDescription(description);
            response.setPrice(price);
            response.setQuantity(quantity);
            response.setTags(new ArrayList<>(tags));
            response.setAvailable(available);
            return response;
        }
    }

    public static ProductSummaryResponseBuilder productSummaryResponseBuilder() {
        return ProductSummaryResponseBuilder.builder().build();
    }

    @Value
    @Builder(toBuilder = true)
    public static class ProductEntityBuilder{
        @Builder.Default
        String id = "product1";
        @Builder.Default
        String sku = "default product-sku";
        @Builder.Default
        String shopId = "default shopId";
        @Builder.Default
        String title = "default title";
        @Builder.Default
        String description = "default description";
        @Builder.Default
        BigDecimal price = BigDecimal.valueOf(400);
        @Builder.Default
        Integer quantity = 300;
        @Builder.Default
        List<String> tags = new ArrayList<>(List.of("default Tags1", "default Tags2", "default Tags3"));
        @Builder.Default
        Boolean available = false;

        public ProductEntity build(){
            ProductEntity entity = new ProductEntity();
            entity.setId(id);
            entity.setShopId(shopId);
            entity.setSku(sku);
            entity.setTitle(title);
            entity.setDescription(description);
            entity.setPrice(price);
            entity.setQuantity(quantity);
            entity.setTags(new ArrayList<>(tags));
            entity.setAvailable(available);
            return entity;
        }
    }

    public static ProductEntityBuilder productEntityBuilder() {
        return ProductEntityBuilder.builder().build();
    }
}
