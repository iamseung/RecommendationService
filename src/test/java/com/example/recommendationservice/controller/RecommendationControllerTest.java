package com.example.recommendationservice.controller;

import com.example.recommendationservice.domain.dto.response.*;
import com.example.recommendationservice.service.RecommendationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean private RecommendationService recommendationService;

    public RecommendationControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[API][GET] 카테고리별 최저가 상품 조회")
    @Test
    void whenRequestingMinPricePerCategory_thenReturnsExpectedJson() throws Exception {
        // Given
        List<MinPricePerCategoryItem> items = List.of(
                new MinPricePerCategoryItem("상의", "브랜드A", 10000),
                new MinPricePerCategoryItem("하의", "브랜드B", 15000)
        );
        MinPricePerCategoryResponse response = new MinPricePerCategoryResponse(items, 25000);
        given(recommendationService.getMinPricePerCategory()).willReturn(response);

        String expectedJson = objectMapper.writeValueAsString(ApiSuccessResponse.ok(response));

        // When & Then
        mvc.perform(get("/api/recommendation/product/min-price-per-category"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @DisplayName("[API][GET] 단일 브랜드 최저가 추천")
    @Test
    void whenRequestingLowestTotalPriceBrand_thenReturnsExpectedJson() throws Exception {
        // Given
        BrandLowestPriceDto lowestPrice = new BrandLowestPriceDto("브랜드A", List.of(
                new CategoryPriceDto("상의", 12000),
                new CategoryPriceDto("하의", 18000)
        ), 30000);

        BrandRecommendationResponse response = new BrandRecommendationResponse(lowestPrice);
        given(recommendationService.getBrandWithLowestTotalPrice()).willReturn(response);

        String expectedJson = objectMapper.writeValueAsString(ApiSuccessResponse.ok(response));

        // When & Then
        mvc.perform(get("/api/recommendation/brand/min-total-price"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        then(recommendationService).should().getBrandWithLowestTotalPrice();
    }

    @DisplayName("[API][GET] 카테고리별 가격 범위 조회")
    @Test
    void givenCategoryName_whenRequestingPriceRange_thenReturnsExpectedJson() throws Exception {
        // Given
        String categoryName = "상의";
        BrandWithPriceDto lowestPrice = new BrandWithPriceDto("브랜드A", 11000);
        BrandWithPriceDto highestPrice = new BrandWithPriceDto("브랜드B", 25000);

        MinAndMaxPriceProductsResponse response = new MinAndMaxPriceProductsResponse(categoryName, lowestPrice, highestPrice);

        given(recommendationService.getMinAndMaxPriceProducts(categoryName)).willReturn(response);

        String expectedJson = objectMapper.writeValueAsString(ApiSuccessResponse.ok(response));

        // When & Then
        mvc.perform(get("/api/recommendation/category/price-range")
                        .param("categoryName", categoryName))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        then(recommendationService).should().getMinAndMaxPriceProducts(categoryName);
    }

}
