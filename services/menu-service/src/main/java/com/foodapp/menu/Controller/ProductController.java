package com.foodapp.menu.Controller;

import com.foodapp.menu.Controller.DTO.PageResponse;
import com.foodapp.menu.Controller.DTO.ProductSummaryResponse;
import com.foodapp.menu.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.base-path}/products",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{shopSlug}")
    public ResponseEntity<PageResponse<ProductSummaryResponse>> getAllByPage(@PageableDefault(size = 20) Pageable pageable,
                                                                             @PathVariable String shopSlug) {
        PageResponse<ProductSummaryResponse> product = productService.getAllPageable(shopSlug, pageable);

        return ResponseEntity
                .ok(product);

    }

}
