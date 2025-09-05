package com.foodapp.menu.Controller;

import com.foodapp.menu.Controller.DTO.ShopCreateRequest;
import com.foodapp.menu.Controller.DTO.ShopSummaryResponse;
import com.foodapp.menu.Service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base-path}/shops")
public class ShopController {

    private final ShopService shopService;

    @PostMapping("/create")
    public ResponseEntity<ShopSummaryResponse> createShop(@Valid @RequestBody ShopCreateRequest request){
        ShopSummaryResponse shop = shopService.createShop(request);
        URI location = URI.create("/api/v1/shops/%s".formatted(shop.getSlug()));

        return ResponseEntity
                .created(location)
                .body(shop);
    }

}
