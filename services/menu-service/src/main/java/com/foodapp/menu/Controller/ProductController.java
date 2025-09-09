package com.foodapp.menu.Controller;

import com.foodapp.menu.Controller.DTO.PageResponse;
import com.foodapp.menu.Controller.DTO.ProductCreateRequest;
import com.foodapp.menu.Controller.DTO.ProductSummaryResponse;
import com.foodapp.menu.Controller.DTO.ProductUpdateRequest;
import com.foodapp.menu.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.base-path}/shops/{shopSlug}/products",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<PageResponse<ProductSummaryResponse>> getAllByPage(@PageableDefault(size = 20) Pageable pageable,
                                                                             @PathVariable String shopSlug) {
        PageResponse<ProductSummaryResponse> product = productService.getAllPageable(shopSlug, pageable);

        return ResponseEntity
                .ok(product);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductSummaryResponse> getProductBySku(@PathVariable String shopSlug,
                                                                  @PathVariable String sku) {
        ProductSummaryResponse product = productService.getBySku(shopSlug, sku);

        return ResponseEntity
                .ok(product);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductSummaryResponse> createProduct(@PathVariable String shopSlug,
                                                                @Valid @RequestBody ProductCreateRequest request) {

        ProductSummaryResponse product = productService.createProduct(shopSlug, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{sku}")
                .buildAndExpand(product.getSku())
                .toUri();


        return ResponseEntity
                .created(location)
                .body(product);
    }

    @PatchMapping(value = "/{sku}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductSummaryResponse> updateProduct(@PathVariable String shopSlug,
                                                                @PathVariable String sku,
                                                                @Valid @RequestBody ProductUpdateRequest request) {
        ProductSummaryResponse product = productService.updateProduct(shopSlug, sku, request);

        return ResponseEntity
                .ok(product);
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String shopSlug,
                                              @PathVariable String sku) {
        productService.deleteProduct(shopSlug, sku);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}