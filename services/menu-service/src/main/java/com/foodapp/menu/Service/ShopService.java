package com.foodapp.menu.Service;

import com.foodapp.menu.Controller.DTO.ShopCreateRequest;
import com.foodapp.menu.Controller.DTO.ShopSummaryResponse;
import com.foodapp.menu.Controller.DTO.ShopUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface ShopService {

    ShopSummaryResponse createShop(ShopCreateRequest request);

    ShopSummaryResponse updateShop(String slug, ShopUpdateRequest request);

    void deleteShop(String slug);

    List<ShopSummaryResponse> getAll();

    ShopSummaryResponse getBySlug(String slug);
}
