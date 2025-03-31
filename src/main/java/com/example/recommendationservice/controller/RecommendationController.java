package com.example.recommendationservice.controller;

import com.example.recommendationservice.domain.dto.response.ApiSuccessResponse;
import com.example.recommendationservice.domain.dto.response.BrandRecommendationResponse;
import com.example.recommendationservice.domain.dto.response.MinAndMaxPriceProductsResponse;
import com.example.recommendationservice.domain.dto.response.MinPricePerCategoryResponse;
import com.example.recommendationservice.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "추천 API", description = "최저가 상품 추천, 브랜드 추천, 가격 범위 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    // 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
    @Operation(summary = "카테고리별 최저가 상품 조회", description = "각 카테고리에서 가장 저렴한 브랜드 상품을 조회하고 총액을 계산합니다.")
    @GetMapping("/product/min-price-per-category")
    public ResponseEntity<ApiSuccessResponse<MinPricePerCategoryResponse>> getMinPriceProductPerCategory() {
        MinPricePerCategoryResponse response = recommendationService.getMinPricePerCategory();
        return ResponseEntity.ok(ApiSuccessResponse.ok(response));
    }

    // 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
    @Operation(summary = "단일 브랜드 최저가 추천", description = "모든 카테고리를 단일 브랜드 기준으로 구매할 때 가장 저렴한 브랜드를 추천합니다.")
    @GetMapping("/brand/min-total-price")
    public ResponseEntity<ApiSuccessResponse<BrandRecommendationResponse>> getBrandWithLowestTotalPrice() {
        BrandRecommendationResponse response = recommendationService.getBrandWithLowestTotalPrice();
        return ResponseEntity.ok(ApiSuccessResponse.ok(response));
    }

    // 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
    @Operation(summary = "카테고리별 가격 범위 조회", description = "카테고리명을 기준으로 최저가/최고가 브랜드의 상품을 조회합니다.")
    @GetMapping("/category/price-range")
    public ResponseEntity<ApiSuccessResponse<MinAndMaxPriceProductsResponse>> getPriceRangeByCategory(String categoryName) {
        MinAndMaxPriceProductsResponse response = recommendationService.getMinAndMaxPriceProducts(categoryName);
        return ResponseEntity.ok(ApiSuccessResponse.ok(response));
    }
}
