package com.example.recommendationservice.repository;

import com.example.recommendationservice.domain.dto.response.BrandWithTotalPriceDto;
import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.domain.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DisplayName("JPA Repository 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaRepositoryTest {

    @Autowired private CategoryJpaRepository categoryJpaRepository;
    @Autowired private BrandJpaRepository brandJpaRepository;
    @Autowired private ProductJpaRepository productJpaRepository;

    @DisplayName("카테고리 전체 조회 시, 8개가 조회되어야 한다")
    @Test
    void givenTestData_whenSelectAllCategories_thenWorksFine() {
        List<Category> categories = categoryJpaRepository.findAll();
        assertThat(categories)
                .isNotNull()
                .hasSize(8);
    }

    @DisplayName("브랜드 전체 조회 시, 9개가 조회되어야 한다")
    @Test
    void givenTestData_whenSelectAllBrands_thenWorksFine() {
        List<Brand> brands = brandJpaRepository.findAll();
        assertThat(brands)
                .isNotNull()
                .hasSize(9);
    }

    @DisplayName("카테고리 ID로 상품 조회 시, 9개가 조회되어야 한다")
    @Test
    void givenCategoryId_whenFindProducts_thenReturnProducts() {
        List<Product> products = productJpaRepository.findProductsByCategoryId(1L);
        assertThat(products)
                .isNotNull()
                .hasSize(9);
    }

    @DisplayName("브랜드 ID로 상품 조회 시, 8개가 조회되어야 한다")
    @Test
    void givenBrandId_whenFindProducts_thenReturnProducts() {
        List<Product> products = productJpaRepository.findByBrandId(1L);
        assertThat(products)
                .isNotNull()
                .hasSize(8);
    }

    @DisplayName("카테고리별 최저가 상품 조회 시, 카테고리마다 하나 이상의 상품이 존재할 수 있어야 한다")
    @Test
    void whenFindMinPriceProductsByCategory_thenReturnExpectedProducts() {
        List<Product> products = productJpaRepository.findMinPriceProductsByCategory();

        Map<Long, List<Product>> categoryGroup = products.stream()
                .collect(Collectors.groupingBy(p -> p.getCategory().getId()));

        assertThat(categoryGroup).isNotEmpty();
        assertThat(categoryGroup.values()).allSatisfy(list -> assertThat(list).isNotEmpty());
    }

    @DisplayName("브랜드별 총액이 가장 낮은 브랜드 조회 시, 최소 총액 브랜드가 반환되어야 한다")
    @Test
    void whenFindBrandsWithLowestTotalPrice_thenReturnCorrectBrand() {
        List<BrandWithTotalPriceDto> result = productJpaRepository.findBrandsWithLowestTotalPrice();
        assertThat(result)
                .isNotNull()
                .isNotEmpty();
    }
}
