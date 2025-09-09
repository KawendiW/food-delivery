package com.foodapp.menu.Service;

import com.foodapp.menu.Controller.DTO.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductSummaryResponse createProduct(String shopSlug, ProductCreateRequest request);

    ProductSummaryResponse updateProduct(String shopSlug, String sku, ProductUpdateRequest request);

    void deleteProduct(String shopSlug, String sku);

    List<ProductSummaryResponse> getAll();

    PageResponse<ProductSummaryResponse> getAllPageable(String shopSlug, Pageable pageable);

    ProductSummaryResponse getBySku(String shopSlug, String sku);

}
