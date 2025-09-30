package com.foodapp.menu.WebMvcTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodapp.menu.Controller.DTO.PageResponse;
import com.foodapp.menu.Controller.DTO.ShopCreateRequest;
import com.foodapp.menu.Controller.DTO.ShopSummaryResponse;
import com.foodapp.menu.Controller.DTO.ShopUpdateRequest;
import com.foodapp.menu.Controller.ShopController;
import com.foodapp.menu.Fixtures.ShopMothers;
import com.foodapp.menu.Service.ShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ShopController.class)
public class ShopControllerMvcTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    private ShopService shopService;

    private final String URL_PATH = "/api/v1/shops";
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/v1/shops: returns code 200 and correct pagination")
    public void getAllShop_returns200_andPage() throws Exception {
        PageRequest pageable = PageRequest.of(0, 2);
        PageResponse<ShopSummaryResponse> pageResponse = new PageResponse<>(
                List.of(new ShopSummaryResponse(), new ShopSummaryResponse()),
                2, 0, 2, false
        );

        when(shopService.getAllPageable(any()))
                .thenReturn(pageResponse);

        mockMvc.perform(get(URL_PATH)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(pageResponse)));

        verify(shopService).getAllPageable(any());
    }

    @Test
    @DisplayName("GET /api/v1/shops/{slug}: returns code 200 and shop")
    public void getShopBySlug_returns200_withBody() throws Exception {
        ShopSummaryResponse shopSummaryResponse = ShopMothers.shopSummaryResponseBuilder().build();

        when(shopService.getBySlug("shop-slug"))
                .thenReturn(shopSummaryResponse);

        mockMvc.perform(get(URL_PATH + "/shop-slug"))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(shopSummaryResponse)));

        verify(shopService).getBySlug("shop-slug");
    }

    @Test
    @DisplayName("POST /api/v1/shops: returns 201 with shop and redirect")
    public void createShop_returns201_withLocationAndBody() throws Exception {
        ShopCreateRequest shopCreateRequest = ShopMothers.shopCreateRequestBuilder().build();
        ShopSummaryResponse shopSummaryResponse = ShopMothers.shopSummaryResponseBuilder().build();

        when(shopService.createShop(any(ShopCreateRequest.class)))
                .thenReturn(shopSummaryResponse);

        mockMvc.perform(post(URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shopCreateRequest)))
                .andExpectAll(
                        status().isCreated(),
                        header().exists(HttpHeaders.LOCATION),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(shopSummaryResponse)));

        verify(shopService).createShop(any(ShopCreateRequest.class));
    }

    @Test
    @DisplayName("PATCH /api/v1/shops/{slug}: returns 200 with shop")
    public void updateShop_returns200_andBody() throws Exception {
        ShopUpdateRequest shopUpdateRequest = ShopMothers.shopUpdateRequestBuilder().build();
        ShopSummaryResponse shopSummaryResponse = ShopMothers.shopSummaryResponseBuilder().build();

        when(shopService.updateShop(eq("shop-slug"), any(ShopUpdateRequest.class)))
                .thenReturn(shopSummaryResponse);

        mockMvc.perform(patch(URL_PATH + "/shop-slug")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shopUpdateRequest)))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json(objectMapper.writeValueAsString(shopSummaryResponse)));

        verify(shopService).updateShop(eq("shop-slug"), any(ShopUpdateRequest.class));
    }

    @Test
    @DisplayName("DELETE /api/v1/shops/{slug}: returns 204")
    public void deleteShop_returns204_andNothing() throws Exception{

        mockMvc.perform(delete(URL_PATH + "/shop-slug"))
                .andExpect(status().isNoContent());

        verify(shopService).deleteShop("shop-slug");
    }
}
