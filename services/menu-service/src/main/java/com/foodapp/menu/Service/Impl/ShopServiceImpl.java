package com.foodapp.menu.Service.Impl;

import com.foodapp.menu.Controller.DTO.PageResponse;
import com.foodapp.menu.Controller.DTO.ShopCreateRequest;
import com.foodapp.menu.Controller.DTO.ShopSummaryResponse;
import com.foodapp.menu.Controller.DTO.ShopUpdateRequest;
import com.foodapp.menu.Entity.ShopEntity;
import com.foodapp.menu.MapStruct.ShopMapper;
import com.foodapp.menu.Repository.ShopRepository;
import com.foodapp.menu.Service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    @Override
    public ShopSummaryResponse createShop(ShopCreateRequest request) {
        ShopEntity entity = new ShopEntity();

        String slugify = slugify(request.getName());
        String slug = ensureUniqueSlug(slugify);

        entity.setSlug(slug);
        entity.setId(UUID.randomUUID().toString());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setAddresses(request.getAddresses());
        entity.setWorkingHours(request.getWorkingHours());
        entity.setAvailable(true);

        try {
            shopRepository.insert(entity);
        } catch (DuplicateKeyException exception) {
            throw new DuplicateKeyException("slug already exists");
        }

        return shopMapper.toSummary(entity);
    }

    @Override
    public ShopSummaryResponse updateShop(String slug, ShopUpdateRequest request) {
        ShopEntity entity = shopRepository.findBySlug(slug).orElseThrow();
        shopMapper.update(entity, request);

        ShopEntity saved = shopRepository.save(entity);

        return shopMapper.toSummary(saved);
    }

    @Override
    public void deleteShop(String slug) {
        shopRepository.deleteBySlug(slug);
    }

    @Override
    public List<ShopSummaryResponse> getAll() {
        List<ShopEntity> shopEntities = shopRepository.findAll();

        return shopMapper.toSummaryList(shopEntities);
    }

    @Override
    public PageResponse<ShopSummaryResponse> getAllPageable(Pageable pageable) {
        Page<ShopEntity> shop = shopRepository.findAll(pageable);
        List<ShopSummaryResponse> content = shop.map(shopMapper::toSummary).getContent();

        return new PageResponse<>(
                content,
                shop.getTotalElements(),
                shop.getNumber(),
                shop.getSize(),
                shop.hasNext()
        );
    }

    @Override
    public ShopSummaryResponse getBySlug(String slug) {
        ShopEntity entity = shopRepository.findBySlug(slug).orElseThrow();

        return shopMapper.toSummary(entity);
    }


    private String ensureUniqueSlug(String slug) {
        String s = slug;
        int i = 2;

        while (shopRepository.existsBySlug(s)) {
            s = slug + "-" + i++;
            if (i > 50) {
                s = slug + "-" + UUID.randomUUID().toString().substring(0, 6);
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
        if (x.length() < 3) x = (x + "-shop").replaceAll("-{2,}", "-");

        return x;
    }
}
