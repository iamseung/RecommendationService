package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.model.BrandModel;
import com.example.recommendationservice.exception.ErrorCode;
import com.example.recommendationservice.exception.BaseException;
import com.example.recommendationservice.repository.BrandJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandJpaRepository brandJpaRepository;

    @Transactional
    @Override
    public void createBrand(BrandModel brandModel) {
        validateDuplicateBrand(brandModel.getBrandName());
        brandJpaRepository.save(brandModel.toEntity());
    }

    private void validateDuplicateBrand(String brandName) {
        brandJpaRepository.findByName(brandName)
                .ifPresent(b -> {
                    throw new BaseException(ErrorCode.DUPLICATE_BRAND_NAME, "해당 이름의 브랜드는 이미 존재합니다. brandName : %s".formatted(brandName));
                });
    }

    @Transactional
    @Override
    public void updateBrand(Long brandId, BrandModel brandModel) {
        try {
            Brand brand = brandJpaRepository.getReferenceById(brandId);
            brand.update(brandModel.getBrandName());
        } catch (EntityNotFoundException e) {
            throw new BaseException(ErrorCode.NOT_EXIST_BRAND, "브랜드가 존재하지 않습니다. brandId : %s".formatted(brandId));
        }
    }

    @Transactional
    @Override
    public void deleteBrand(Long brandId) {
        brandJpaRepository.deleteById(brandId);
    }

    @Transactional(readOnly = true)
    public Brand findById(Long brandId) {
        return brandJpaRepository.findById(brandId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_BRAND, "브랜드가 존재하지 않습니다. brandId : %s".formatted(brandId)));
    }
}
