package com.foodapp.menu.Controller;

import com.foodapp.menu.Controller.DTO.ShopCreateRequest;
import com.foodapp.menu.Controller.DTO.ShopSummaryResponse;
import com.foodapp.menu.Controller.DTO.ShopUpdateRequest;
import com.foodapp.menu.Service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = "${api.base-path}/shops",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {

    private final ShopService shopService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShopSummaryResponse> createShop(@Valid @RequestBody ShopCreateRequest request) {
        ShopSummaryResponse shop = shopService.createShop(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{slug}")
                .buildAndExpand(shop.getSlug())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(shop);
    }

    @PatchMapping(path = "/{slug}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShopSummaryResponse> updateShop(@Valid @RequestBody ShopUpdateRequest request,
                                                          @PathVariable String slug) {
        ShopSummaryResponse shop = shopService.updateShop(slug, request);

        return ResponseEntity
                .ok(shop);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteShop(@PathVariable String slug) {
        shopService.deleteShop(slug);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }

}
