package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.dto.response.BrandRecommendationResponse;
import com.example.recommendationservice.domain.dto.response.BrandWithTotalPriceDto;
import com.example.recommendationservice.domain.dto.response.MinAndMaxPriceProductsResponse;
import com.example.recommendationservice.domain.dto.response.MinPricePerCategoryResponse;
import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.domain.entity.Product;
import com.example.recommendationservice.strategy.BrandSelectionStrategy;
import com.example.recommendationservice.strategy.MinAndMaxCategorySelectionStrategy;
import com.example.recommendationservice.domain.dto.response.MinMaxProductDto;
import com.example.recommendationservice.strategy.MinPriceCategorySelectionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final MinPriceCategorySelectionStrategy minPriceCategorySelectionStrategy;
    private final BrandSelectionStrategy brandSelectionStrategy;
    private final MinAndMaxCategorySelectionStrategy minAndMaxCategorySelectionStrategy;

    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;

    @Override
    public MinPricePerCategoryResponse getMinPricePerCategory() {
        // FindFirstMinPriceProductStrategy, 금액이 같다면 카테고리 ID 를 기준으로 선정 (오름차순)
        List<Product> minPriceProducts = minPriceCategorySelectionStrategy.apply(productService.findMinPriceProductsByCategory());

        return MinPricePerCategoryResponse.from(minPriceProducts);
    }

    @Override
    public BrandRecommendationResponse getBrandWithLowestTotalPrice() {
        List<BrandWithTotalPriceDto> brandsWithLowestTotalPrice = productService.findBrandsWithLowestTotalPrice();

        // FindFirstBrandSelectionStrategy, 금액이 같다면 브랜드 ID 를 기준으로 선정 (오름차순)
        Long selectedBrandId = brandSelectionStrategy.selectBrandId(brandsWithLowestTotalPrice);
        List<Product> products = productService.findByBrandId(selectedBrandId);

        Brand brand = brandService.findById(selectedBrandId);

        return BrandRecommendationResponse.from(brand, products);
    }

    @Override
    public MinAndMaxPriceProductsResponse getMinAndMaxPriceProducts(String categoryName) {
        Category category = categoryService.findByName(categoryName);
        List<Product> products = productService.findProductsByCategoryId(category.getId());

        // BrandSortedMinMaxStrategy, 금액이 같다면 브랜드 ID 를 기준으로 선정
        MinMaxProductDto minMaxProducts = minAndMaxCategorySelectionStrategy.apply(products);

        return MinAndMaxPriceProductsResponse.from(category, minMaxProducts);
    }
}
