package com.foodapp.menu.UnitTest;

import com.foodapp.menu.Controller.DTO.*;
import com.foodapp.menu.Entity.ShopEntity;
import com.foodapp.menu.MapStruct.ShopMapper;
import com.foodapp.menu.Repository.ShopRepository;
import com.foodapp.menu.Service.Impl.ShopServiceImpl;
import com.foodapp.menu.Fixtures.ShopMothers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ShopServiceUnitTest {

    @Mock
    private ShopRepository shopRepository;

    @Spy
    private ShopMapper shopMapper = Mappers.getMapper(ShopMapper.class);

    @InjectMocks
    private ShopServiceImpl shopService;

    @Test
    @DisplayName("createShop: generates slug saving entity and returns dto")
    public void createShop_withValidRequest_generatesSlugAndReturnsDto() {
        ArgumentCaptor<ShopEntity> shopSaved = ArgumentCaptor.forClass(ShopEntity.class);
        when(shopRepository.insert(shopSaved.capture()))
                .thenAnswer(invocation -> {
                    ShopEntity shop = invocation.getArgument(0);
                    shop.setId("shop1");
                    return shop;
                });

        ShopCreateRequest shopCreateRequest = ShopMothers.shopCreateRequestBuilder().build();

        ShopSummaryResponse shopSummaryResponse = shopService.createShop(shopCreateRequest);

        ShopEntity inserted = shopSaved.getValue();
        assertThat(inserted.getId()).isEqualTo("shop1");
        assertThat(inserted.getSlug()).isEqualTo(shopSummaryResponse.getSlug());
        assertThat(inserted.getName()).isEqualTo(shopCreateRequest.getName());

        verify(shopMapper).toSummary(any(ShopEntity.class));
        verify(shopRepository).insert(any(ShopEntity.class));
    }

    @Test
    @DisplayName("updateShop: shop updates and saving without change slug")
    public void updateShop_whenValidationPassed_savesShop() {
        ShopEntity existingShop = ShopMothers.shopEntityBuilder().build();
        when(shopRepository.findBySlug("shop-slug"))
                .thenReturn(Optional.of(existingShop));

        ShopUpdateRequest shopUpdateRequest = ShopMothers.shopUpdateRequestBuilder().build();

        when(shopRepository.save(existingShop))
                .thenReturn(existingShop);

        ShopSummaryResponse shopSummaryResponse = shopService.updateShop("shop-slug", shopUpdateRequest);

        assertSoftly(soft -> {
            soft.assertThat(existingShop.getSlug()).isEqualTo("shop-slug");
            soft.assertThat(shopSummaryResponse.getName()).isEqualTo(shopUpdateRequest.getName());
            soft.assertThat(shopSummaryResponse.getDescription()).isEqualTo(shopUpdateRequest.getDescription());
            soft.assertThat(shopSummaryResponse.getAddresses()).isEqualTo(shopUpdateRequest.getAddresses());
            soft.assertThat(shopSummaryResponse.getWorkingHours()).isEqualTo(shopUpdateRequest.getWorkingHours());
        });

        InOrder order = inOrder(shopRepository, shopMapper);
        order.verify(shopRepository).findBySlug("shop-slug");
        order.verify(shopMapper).update(existingShop, shopUpdateRequest);
        order.verify(shopRepository).save(existingShop);
        order.verify(shopMapper).toSummary(existingShop);
    }

    @Test
    @DisplayName("deleteShop: checking exist shop and delete him")
    public void deleteShop_whenExists_deleteOnce() {
        shopService.deleteShop("shop-slug");
        verify(shopRepository).deleteBySlug("shop-slug");
    }

    @Test
    @DisplayName("get all pageable: finding all shop and returns with pageable")
    public void getAllPageable_whenExist_returnsPageableShops() {
        Pageable pageable = PageRequest.of(0, 2);

        ShopEntity existingShop1 = ShopMothers.shopEntityBuilder().build();
        ShopEntity existingShop2 = ShopMothers.shopEntityBuilder().toBuilder()
                .id("shop2")
                .name("default name2")
                .slug("default slug2")
                .build()
                .build();

        Page<ShopEntity> page = new PageImpl<>(List.of(existingShop1, existingShop2), pageable, 5);
        when(shopRepository.findAll(pageable))
                .thenReturn(page);

        PageResponse<ShopSummaryResponse> response = shopService.getAllPageable(pageable);

        assertSoftly(soft -> {
            soft.assertThat(response.getContent()).hasSize(2);
            soft.assertThat(response.getContent()).extracting(ShopSummaryResponse::getSlug)
                    .containsExactly(existingShop1.getSlug(), existingShop2.getSlug());
            soft.assertThat(response.getTotal()).isEqualTo(5);
            soft.assertThat(response.getPage()).isEqualTo(0);
            soft.assertThat(response.getSize()).isEqualTo(2);
            soft.assertThat(response.isHasNext()).isTrue();
        });

        verify(shopRepository).findAll(pageable);
    }

    @Test
    @DisplayName("get by sku: finding shop by slug and returns dto")
    public void getBySlug_whenExist_returnsDto() {
        ShopEntity existingShop = ShopMothers.shopEntityBuilder().build();
        when(shopRepository.findBySlug("shop-slug"))
                .thenReturn(Optional.of(existingShop));

        ShopSummaryResponse shopSummaryResponse = shopService.getBySlug("shop-slug");

        assertSoftly(soft -> {
            soft.assertThat(existingShop.getSlug()).isEqualTo("shop-slug");
            soft.assertThat(shopSummaryResponse.getName()).isEqualTo(existingShop.getName());
            soft.assertThat(shopSummaryResponse.getDescription()).isEqualTo(existingShop.getDescription());
            soft.assertThat(shopSummaryResponse.getAddresses()).isEqualTo(existingShop.getAddresses());
            soft.assertThat(shopSummaryResponse.getWorkingHours()).isEqualTo(existingShop.getWorkingHours());
        });

        verify(shopRepository).findBySlug("shop-slug");
        verify(shopMapper).toSummary(existingShop);
    }
}
