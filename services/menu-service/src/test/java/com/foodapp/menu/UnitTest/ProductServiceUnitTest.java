package com.foodapp.menu.UnitTest;

import com.foodapp.menu.Controller.DTO.PageResponse;
import com.foodapp.menu.Controller.DTO.ProductCreateRequest;
import com.foodapp.menu.Controller.DTO.ProductSummaryResponse;
import com.foodapp.menu.Controller.DTO.ProductUpdateRequest;
import com.foodapp.menu.Entity.ProductEntity;
import com.foodapp.menu.Entity.ShopEntity;
import com.foodapp.menu.MapStruct.ProductMapper;
import com.foodapp.menu.Repository.ProductRepository;
import com.foodapp.menu.Repository.ShopRepository;
import com.foodapp.menu.Service.Impl.ProductServiceImpl;
import com.foodapp.menu.Service.ShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @Mock
    ShopRepository shopRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    ShopService shopService;
    @Spy
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    @DisplayName("createProduct: generates Sku and saving with correct shopId")
    public void createProduct_withValidRequest_generatesSkuAndSavesWithShopId() {
        ArgumentCaptor<ProductEntity> productSaved = ArgumentCaptor.forClass(ProductEntity.class);
        when(productRepository.insert(productSaved.capture()))
                .thenAnswer(invocation -> {
                    ProductEntity product = productSaved.getValue();
                    product.setId("product1");
                    product.setShopId("shop1");
                    return product;
                });

        ProductCreateRequest productCreateRequest = new ProductCreateRequest();
        productCreateRequest.setTitle("Test title");
        productCreateRequest.setDescription("Test description");
        productCreateRequest.setPrice(BigDecimal.valueOf(1450));
        productCreateRequest.setTags(List.of("tags1", "tags2", "tags3"));
        productCreateRequest.setQuantity(20);

        ProductSummaryResponse responseDto = productService.createProduct("shop-slug", productCreateRequest);

        ProductEntity inserted = productSaved.getValue();
        assertThat(inserted.getShopId()).isEqualTo("shop1");
        assertThat(inserted.getSku()).isNotBlank();
        assertThat(responseDto.getSku()).isEqualTo(inserted.getSku());
        verify(productRepository).insert(any(ProductEntity.class));
    }

    @Test
    @DisplayName("createProduct: throw exception when Sku is already exists")
    public void createProduct_whenSkuIsAlreadyExist_throwsDuplicateKeyException() {
        when(shopService.getIdBySlug("shop-slug")).thenReturn("shop1");

        when(productRepository.insert(any(ProductEntity.class)))
                .thenThrow(new DuplicateKeyException(""));

        ProductCreateRequest productCreateRequest = new ProductCreateRequest();
        productCreateRequest.setTitle("Test title");
        productCreateRequest.setDescription("Test description");
        productCreateRequest.setPrice(BigDecimal.valueOf(1450));
        productCreateRequest.setTags(List.of("tags1", "tags2", "tags3"));
        productCreateRequest.setQuantity(20);

        assertThatThrownBy(() -> productService.createProduct("shop-slug", productCreateRequest))
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessageContaining("sku already exists");

        verify(shopService).getIdBySlug("shop-slug");
        verify(productRepository).insert(any(ProductEntity.class));
        verifyNoInteractions(productMapper);
    }

    @Test
    @DisplayName("updateProduct: product updates and saving without changing Sku")
    public void updateProduct_whenValidationPassed_savesProduct() {
        when(shopService.getIdBySlug("shop-slug")).thenReturn("shop1");

        ProductEntity productExisting = new ProductEntity();
        productExisting.setId("product1");
        productExisting.setShopId("shop1");
        productExisting.setSku("productSku");
        productExisting.setTitle("Old title");
        productExisting.setDescription("Old description");
        productExisting.setPrice(BigDecimal.valueOf(200.0));
        productExisting.setTags(new ArrayList<>(List.of("tags1", "tags2", "tags3")));
        productExisting.setQuantity(10);
        productExisting.setAvailable(true);

        when(productRepository.findByShopIdAndSku("shop1", "productSku"))
                .thenReturn(Optional.of(productExisting));

        ArgumentCaptor<ProductEntity> productSaved = ArgumentCaptor.forClass(ProductEntity.class);
        when(productRepository.save(productSaved.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        productUpdateRequest.setTitle("New title");
        productUpdateRequest.setDescription("New description");
        productUpdateRequest.setPrice(BigDecimal.valueOf(100));
        productUpdateRequest.setTags(new ArrayList<>(List.of("new tags1", "new tags2", "new tags3")));
        productUpdateRequest.setQuantity(20);

        ProductSummaryResponse productSummaryResponse = productService.updateProduct
                ("shop-slug", "productSku", productUpdateRequest);

        assertThat(productSummaryResponse.getSku()).isEqualTo("productSku");
        assertThat(productSummaryResponse.getTitle()).isEqualTo("New title");
        assertThat(productSummaryResponse.getDescription()).isEqualTo("New description");
        assertThat(productSummaryResponse.getTags()).isEqualTo(new ArrayList<>(List.of("new tags1", "new tags2", "new tags3")));
        assertThat(productSummaryResponse.getPrice()).isEqualByComparingTo("100");
        assertThat(productSummaryResponse.getQuantity()).isEqualTo(20);

        ProductEntity savedValue = productSaved.getValue();
        assertThat(savedValue.getSku()).isEqualTo("productSku");
        assertThat(savedValue.getShopId()).isEqualTo("shop1");

        verify(shopService).getIdBySlug("shop-slug");
        verify(productRepository).findByShopIdAndSku("shop1", "productSku");
        verify(productMapper).update(productExisting, productUpdateRequest);
        verify(productRepository).save(productExisting);

    }

    @Test
    @DisplayName("delete product: checking exist product and delete him")
    public void deleteProduct_whenExist_deletesOnce() {
        when(shopService.getIdBySlug("shop-slug")).thenReturn("shop1");

        productService.deleteProduct("shop-slug", "productSku");

        verify(productRepository).deleteByShopIdAndSku("shop1", "productSku");
    }

    @Test
    @DisplayName("get products: finding product by shopSlug and get with pageable ")
    public void getProduct_whenExist_returnsPageableProducts() {
        ShopEntity shop = new ShopEntity();
        shop.setId("shop1");
        when(shopRepository.findBySlug("shop-slug")).thenReturn(Optional.of(shop));

        Pageable pageable = PageRequest.of(0, 2);

        ProductEntity productExisting = new ProductEntity();
        productExisting.setId("product1");
        productExisting.setShopId("shop1");
        productExisting.setSku("productSku");
        productExisting.setTitle("title");
        productExisting.setDescription("description");
        productExisting.setPrice(BigDecimal.valueOf(200.0));
        productExisting.setTags(new ArrayList<>(List.of("tags1", "tags2", "tags3")));
        productExisting.setQuantity(10);
        productExisting.setAvailable(true);

        ProductEntity productExisting2 = new ProductEntity();
        productExisting2.setId("product2");
        productExisting2.setShopId("shop1");
        productExisting2.setSku("productSku2");
        productExisting2.setTitle("title2");
        productExisting2.setDescription("description2");
        productExisting2.setPrice(BigDecimal.valueOf(150.0));
        productExisting2.setTags(new ArrayList<>(List.of("tags1", "tags2", "tags3")));
        productExisting2.setQuantity(20);
        productExisting2.setAvailable(false);

        Page<ProductEntity> page = new PageImpl<>(List.of(productExisting, productExisting2), pageable, 5);
        when(productRepository.findAllByShopId("shop1", pageable)).thenReturn(page);

        PageResponse<ProductSummaryResponse> response = productService.getAllPageable("shop-slug", pageable);

        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent()).extracting(ProductSummaryResponse::getSku)
                .containsExactly("productSku", "productSku2");
        assertThat(response.getTotal()).isEqualTo(5);
        assertThat(response.getPage()).isEqualTo(0);
        assertThat(response.getSize()).isEqualTo(2);
        assertThat(response.isHasNext()).isTrue();

        verify(shopRepository).findBySlug("shop-slug");
        verify(productRepository).findAllByShopId("shop1", pageable);
    }

    @Test
    @DisplayName("get product: finding product by Sku and shopSlug and returns one")
    public void getProduct_whenExist_returnsOne(){
        when(shopService.getIdBySlug("shop-slug")).thenReturn("shop1");

        ProductEntity productExisting = new ProductEntity();
        productExisting.setId("product1");
        productExisting.setShopId("shop1");
        productExisting.setSku("productSku");
        productExisting.setTitle("title");
        productExisting.setDescription("description");
        productExisting.setPrice(BigDecimal.valueOf(200.0));
        productExisting.setTags(new ArrayList<>(List.of("tags1", "tags2", "tags3")));
        productExisting.setQuantity(10);
        productExisting.setAvailable(true);

        when(productRepository.findByShopIdAndSku("shop1", "productSku"))
                .thenReturn(Optional.of(productExisting));

        ProductSummaryResponse response = productService.getBySku("shop-slug", "productSku");

        assertThat(response.getSku()).isEqualTo("productSku");
        assertThat(response.getTitle()).isEqualTo("title");

        verify(shopService).getIdBySlug("shop-slug");
        verify(productRepository).findByShopIdAndSku("shop1", "productSku");
        verify(productMapper).toSummary(productExisting);
    }
}
