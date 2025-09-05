package com.foodapp.menu.Service;

import com.foodapp.menu.Controller.DTO.PageResponse;
import com.foodapp.menu.Controller.DTO.ShopCreateRequest;
import com.foodapp.menu.Controller.DTO.ShopSummaryResponse;
import com.foodapp.menu.Controller.DTO.ShopUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShopService {

    ShopSummaryResponse createShop(ShopCreateRequest request);

    ShopSummaryResponse updateShop(String slug, ShopUpdateRequest request);

    void deleteShop(String slug);

    List<ShopSummaryResponse> getAll();

    PageResponse<ShopSummaryResponse> getAllPageable(Pageable pageable);

    ShopSummaryResponse getBySlug(String slug);
}
