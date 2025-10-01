package com.foodapp.menu.WebMvcTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodapp.menu.Controller.DTO.PageResponse;
import com.foodapp.menu.Controller.DTO.ProductCreateRequest;
import com.foodapp.menu.Controller.DTO.ProductSummaryResponse;
import com.foodapp.menu.Controller.DTO.ProductUpdateRequest;
import com.foodapp.menu.Controller.ProductController;
import com.foodapp.menu.Fixtures.ProductMother;
import com.foodapp.menu.Service.ProductService;
import com.foodapp.menu.Service.ShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.print.Pageable;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerMvcTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    private final String URL_PATH = "/api/v1/shops/shop-slug/products";

    @Test
    @DisplayName("GET /api/v1/shops/{shopSlug}/products: returns code 200 and correct pagination")
    public void getAllByPage_returns200_andPage() throws Exception {
        PageRequest pageable = PageRequest.of(0, 2);
        PageResponse<ProductSummaryResponse> pageResponse = new PageResponse<>(
                List.of(new ProductSummaryResponse(), new ProductSummaryResponse()),
                2, 0, 2, false
        );

        when(productService.getAllPageable(eq("shop-slug"), any()))
                .thenReturn(pageResponse);

        mockMvc.perform(get(URL_PATH)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(pageResponse)));

        verify(productService).getAllPageable(eq("shop-slug"), any());
    }

    @Test
    @DisplayName("GET /api/v1/shops/{shopSlug}/products/{sku}: returns code 200 and product")
    public void getProductBySku_returns200_andBody() throws Exception {
        ProductSummaryResponse productSummaryResponse = ProductMother.productSummaryResponseBuilder().build();

        when(productService.getBySku("shop-slug", "product-sku"))
                .thenReturn(productSummaryResponse);

        mockMvc.perform(get(URL_PATH + "/product-sku"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(productSummaryResponse))
                );

        verify(productService).getBySku("shop-slug", "product-sku");
    }

    @Test
    @DisplayName("POST /api/v1/shops/{shopSlug}/products: returns code 201 with location and product")
    public void createProduct_returns201_withLocationAndBody() throws Exception {
        ProductCreateRequest productCreateRequest = ProductMother.productCreateRequestBuilder().build();
        ProductSummaryResponse productSummaryResponse = ProductMother.productSummaryResponseBuilder().build();

        when(productService.createProduct(eq("shop-slug"), any(ProductCreateRequest.class)))
                .thenReturn(productSummaryResponse);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateRequest)))
                .andExpectAll(
                        status().isCreated(),
                        header().exists(HttpHeaders.LOCATION),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(productSummaryResponse)));

        verify(productService).createProduct(eq("shop-slug"), any(ProductCreateRequest.class));
    }

    @Test
    @DisplayName("PATCH /api/v1/shops/{shopSlug}/products/{sku}: returns code 200 and body")
    public void updateProduct_returns200_andBody() throws Exception {
        ProductUpdateRequest productUpdateRequest = ProductMother.productUpdateRequestBuildr().build();
        ProductSummaryResponse productSummaryResponse = ProductMother.productSummaryResponseBuilder().build();

        when(productService.updateProduct(
                eq("shop-slug"), eq("product-sku"), any(ProductUpdateRequest.class)))
                .thenReturn(productSummaryResponse);

        mockMvc.perform(patch(URL_PATH + "/product-sku")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateRequest)))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(productSummaryResponse)));

        verify(productService).updateProduct(
                eq("shop-slug"), eq("product-sku"), any(ProductUpdateRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/shops/{shopSlug}/products/{sku}: returns code 204")
    public void deleteProduct_returns204_andNothing() throws Exception{
        mockMvc.perform(delete(URL_PATH + "/product-sku"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct("shop-slug","product-sku");
    }
}
