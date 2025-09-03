package com.foodapp.menu.Service.Impl;

import com.foodapp.menu.Controller.DTO.ProductCreateRequest;
import com.foodapp.menu.Controller.DTO.ProductSummaryResponse;
import com.foodapp.menu.Controller.DTO.ProductUpdateRequest;
import com.foodapp.menu.Entity.ProductEntity;
import com.foodapp.menu.Entity.ShopEntity;
import com.foodapp.menu.MapStruct.ProductMapper;
import com.foodapp.menu.Repository.ProductRepository;
import com.foodapp.menu.Repository.ShopRepository;
import com.foodapp.menu.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductSummaryResponse createProduct(String shopSlug, ProductCreateRequest request) {
        String shopId = getShopId(shopSlug);

        String slugify = slugify(request.getTitle());
        String sku = ensureUniqueSku(slugify);

        ProductEntity entity = new ProductEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setShopId(shopId);
        entity.setSku(sku);
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setPrice(request.getPrice());
        entity.setQuantity(request.getQuantity());
        entity.setTags(request.getTags());
        entity.setAvailable(true);

        try {
            productRepository.insert(entity);
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException("sku already exists");
        }

        return productMapper.toSummary(entity);
    }


    @Override
    public ProductSummaryResponse updateProduct(String shopSlug, String sku, ProductUpdateRequest request) {
        String shopId = getShopId(shopSlug);
        ProductEntity entity = productRepository.findByShopIdAndSku(shopId, sku).orElseThrow();

        productMapper.update(entity, request);
        productRepository.save(entity);

        return productMapper.toSummary(entity);
    }

    @Override
    public void deleteProduct(String shopSlug, String sku) {
        String shopId = getShopId(shopSlug);
        productRepository.deleteByShopIdAndSku(shopId, sku);
    }

    @Override
    public List<ProductSummaryResponse> getAll() {
        List<ProductEntity> entities = productRepository.findAll();

        return productMapper.toSummaryList(entities);
    }

    @Override
    public ProductSummaryResponse getBySku(String shopSlug, String sku) {
        String shopId = getShopId(shopSlug);
        ProductEntity entity = productRepository.findByShopIdAndSku(shopId, sku).orElseThrow();

        return productMapper.toSummary(entity);
    }


    private String getShopId(String shopSlug) {
        ShopEntity shop = shopRepository.findBySlug(shopSlug).orElseThrow();
        return shop.getId();
    }

    private String ensureUniqueSku(String sku) {
        String s = sku;
        int i = 2;

        while (productRepository.existsBySku(s)) {
            s = sku + "-" + i++;
            if (i > 50) {
                s = sku + "-" + UUID.randomUUID().toString().substring(0, 6);
                break;
            }
        }

        return s;
    }

    private static String slugify(String name) {
        String x = (name == null ? "" : name).trim().toLowerCase();

        x = x
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");

        if (x.length() > 64) x = x.substring(0, 64).replaceAll("-$", "");
        if (x.length() < 3) x = (x + "-product").replaceAll("-{2,}", "-");

        return x;
    }
}

