package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.dto.response.*;
import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.domain.entity.Product;
import com.example.recommendationservice.strategy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("추천 서비스 테스트")
class RecommendationServiceImplTest {

    @InjectMocks private RecommendationServiceImpl sut;

    @Mock private ProductService productService;
    @Mock private BrandService brandService;
    @Mock private CategoryService categoryService;

    @Mock private MinPriceCategorySelectionStrategy minPriceCategorySelectionStrategy;
    @Mock private BrandSelectionStrategy brandSelectionStrategy;
    @Mock private MinAndMaxCategorySelectionStrategy minAndMaxCategorySelectionStrategy;

    @DisplayName("카테고리별 최저가 상품 목록을 반환한다")
    @Test
    void getMinPricePerCategory_returnsFilteredCheapestProducts() {
        // Given
        Category top = createCategory("상의");
        Category pants = createCategory("바지");

        Brand brandA = createBrand("브랜드A");
        Brand brandB = createBrand("브랜드B");

        Product top1 = createProduct(brandA, top, 10_000);
        Product top2 = createProduct(brandB, top, 11_000);
        Product pants1 = createProduct(brandA, pants, 13_000);
        Product pants2 = createProduct(brandB, pants, 12_000);

        List<Product> rawProducts = createList(top1, top2, pants1, pants2);
        List<Product> expectedFiltered = createList(top1, pants2);

        given(productService.findMinPriceProductsByCategory()).willReturn(rawProducts);
        given(minPriceCategorySelectionStrategy.apply(rawProducts)).willReturn(expectedFiltered);

        // When
        MinPricePerCategoryResponse response = sut.getMinPricePerCategory();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getItems()).hasSize(2);
        assertThat(response.getItems())
                .extracting("category")
                .containsExactlyInAnyOrder("상의", "바지");

        assertThat(response.getTotalPrice()).isEqualTo(22_000);
        then(productService).should().findMinPriceProductsByCategory();
        then(minPriceCategorySelectionStrategy).should().apply(rawProducts);
    }

    @DisplayName("최저 총액이 동일한 브랜드 중, ID가 가장 낮은 브랜드를 추천한다")
    @Test
    void getBrandWithLowestTotalPrice_returnsBrandWithLowestIdWhenTotalIsSame() {
        // Given
        BrandWithTotalPriceDto brand1 = createBrandWithTotalPriceDto(1L, 22_000);
        BrandWithTotalPriceDto brand2 = createBrandWithTotalPriceDto(2L, 22_000);
        BrandWithTotalPriceDto brand3 = createBrandWithTotalPriceDto(3L, 22_000);
        List<BrandWithTotalPriceDto> brandStats = List.of(brand3, brand2, brand1);

        Brand selectedBrand = createBrand("브랜드A");
        Category top = createCategory("상의");
        Category pants = createCategory("바지");

        Product top1 = createProduct(selectedBrand, top, 10_000);
        Product pants1 = createProduct(selectedBrand, pants, 12_000);
        List<Product> products = createList(top1, pants1);

        given(productService.findBrandsWithLowestTotalPrice()).willReturn(brandStats);
        given(brandSelectionStrategy.selectBrandId(brandStats)).willReturn(1L);
        given(productService.findByBrandId(1L)).willReturn(products);
        given(brandService.findById(1L)).willReturn(selectedBrand);

        // When
        BrandRecommendationResponse response = sut.getBrandWithLowestTotalPrice();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getLowestPrice().getBrand()).isEqualTo("브랜드A");
        assertThat(response.getLowestPrice().getTotalPrice()).isEqualTo(22_000);
        assertThat(response.getLowestPrice().getCategory())
                .extracting("category")
                .containsExactlyInAnyOrder("상의", "바지");

        then(productService).should().findBrandsWithLowestTotalPrice();
        then(brandSelectionStrategy).should().selectBrandId(brandStats);
        then(productService).should().findByBrandId(1L);
        then(brandService).should().findById(1L);
    }


    @DisplayName("카테고리 이름으로 최저/최고가 상품을 조회한다 - 동일한 가격일 경우 브랜드 ID가 더 낮은 것을 선택한다")
    @Test
    void getMinAndMaxPriceProducts_returnsMinAndMaxProducts_withBrandIdTieBreaker() {
        // Given
        String categoryName = "상의";
        Category category = createCategory("상의");

        // 브랜드 ID가 낮은 브랜드A가 최저가로 선택되어야 함
        Brand brandA = createBrand(1L, "브랜드A");
        Brand brandB = createBrand(2L, "브랜드B");

        Product minProductCandidate1 = createProduct(brandA, category, 10_000); // 선택 대상
        Product minProductCandidate2 = createProduct(brandB, category, 10_000); // 동일 가격, 제외
        Product maxProduct = createProduct(brandB, category, 15_000);

        List<Product> allProducts = createList(minProductCandidate1, minProductCandidate2, maxProduct);
        MinMaxProductDto minMax = new MinMaxProductDto(minProductCandidate1, maxProduct);

        given(categoryService.findByName(categoryName)).willReturn(category);
        given(productService.findProductsByCategoryId(category.getId())).willReturn(allProducts);
        given(minAndMaxCategorySelectionStrategy.apply(allProducts)).willReturn(minMax);

        // When
        MinAndMaxPriceProductsResponse response = sut.getMinAndMaxPriceProducts(categoryName);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getCategory()).isEqualTo("상의");

        assertThat(response.getLowestPrice().getBrand()).isEqualTo("브랜드A");
        assertThat(response.getLowestPrice().getPrice()).isEqualTo(10_000);

        assertThat(response.getHighestPrice().getBrand()).isEqualTo("브랜드B");
        assertThat(response.getHighestPrice().getPrice()).isEqualTo(15_000);

        then(categoryService).should().findByName(categoryName);
        then(productService).should().findProductsByCategoryId(category.getId());
        then(minAndMaxCategorySelectionStrategy).should().apply(allProducts);
    }

    public Category createCategory(String name) {
        return Category.of(name);
    }

    public Brand createBrand(String name) {
        return Brand.of(name);
    }

    public Brand createBrand(Long id, String name) {
        return Brand.of(id, name);
    }

    public Product createProduct(Brand brand, Category category, int price) {
        return Product.of(brand, category, price);
    }

    public List<Product> createList(Product ... products) {
        return List.of(products);
    }

    public BrandWithTotalPriceDto createBrandWithTotalPriceDto(Long brandId, int totalPrice) {
        return new BrandWithTotalPriceDto() {
            @Override public Long getBrandId() { return brandId; }
            @Override public Integer getTotalPrice() { return totalPrice; }
        };
    }
}
