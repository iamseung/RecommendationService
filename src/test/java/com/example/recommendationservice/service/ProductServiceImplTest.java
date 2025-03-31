package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.dto.response.BrandWithTotalPriceDto;
import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.domain.entity.Product;
import com.example.recommendationservice.domain.model.ProductModel;
import com.example.recommendationservice.exception.BaseException;
import com.example.recommendationservice.repository.ProductJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 상품")
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks ProductServiceImpl sut;
    @Mock ProductJpaRepository productJpaRepository;
    @Mock BrandService brandService;
    @Mock CategoryService categoryService;

    @DisplayName("상품 정보를 입력하면 상품을 생성한다")
    @Test
    void givenProductModel_whenCreatingProduct_thenSavesProduct() {
        // Given
        ProductModel model = new ProductModel(1L, 1L, 1000);
        given(brandService.findById(1L)).willReturn(Brand.of("TestBrand"));
        given(categoryService.findById(1L)).willReturn(Category.of("TestCategory"));
        given(productJpaRepository.save(any(Product.class))).willReturn(null);

        // When & Then
        assertThatCode(() -> sut.createProduct(model)).doesNotThrowAnyException();
        then(productJpaRepository).should().save(any(Product.class));
    }

    @DisplayName("상품 ID와 수정 정보를 입력하면 상품을 수정한다")
    @Test
    void givenProductIdAndModel_whenUpdatingProduct_thenUpdatesProduct() {
        // Given
        Long productId = 1L;
        Product existingProduct = mock(Product.class);
        ProductModel model = new ProductModel(2L, 2L, 2000);

        given(productJpaRepository.getReferenceById(productId)).willReturn(existingProduct);
        given(brandService.findById(2L)).willReturn(Brand.of("UpdatedBrand"));
        given(categoryService.findById(2L)).willReturn(Category.of("UpdatedCategory"));

        // When
        sut.updateProduct(productId, model);

        // Then
        then(existingProduct).should().update(any(Brand.class), any(Category.class), eq(2000));
    }

    @DisplayName("존재하지 않는 상품을 수정하려 하면 예외를 던진다")
    @Test
    void givenInvalidProductId_whenUpdatingProduct_thenThrowsException() {
        // Given
        Long productId = 999L;
        ProductModel model = new ProductModel(1L, 1L, 1000);
        given(productJpaRepository.getReferenceById(productId)).willThrow(EntityNotFoundException.class);

        // When & Then
        assertThatThrownBy(() -> sut.updateProduct(productId, model))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining("상품이 존재하지 않습니다");
    }

    @DisplayName("상품 ID를 입력하면 상품을 삭제한다")
    @Test
    void givenProductId_whenDeletingProduct_thenDeletesProduct() {
        // Given
        Long productId = 1L;
        willDoNothing().given(productJpaRepository).deleteById(productId);

        // When
        sut.deleteProduct(productId);

        // Then
        then(productJpaRepository).should().deleteById(productId);
    }

    @DisplayName("카테고리별 최저가 상품 목록을 조회할 수 있다")
    @Test
    void whenFindingMinPriceProductsByCategory_thenReturnsProductList() {
        // Given
        given(productJpaRepository.findMinPriceProductsByCategory()).willReturn(List.of(mock(Product.class)));

        // When
        List<Product> result = sut.findMinPriceProductsByCategory();

        // Then
        assertThat(result).isNotEmpty();
        then(productJpaRepository).should().findMinPriceProductsByCategory();
    }

    @DisplayName("브랜드별 총합이 가장 낮은 브랜드 목록을 조회할 수 있다")
    @Test
    void whenFindingBrandsWithLowestTotalPrice_thenReturnsDtoList() {
        // Given
        given(productJpaRepository.findBrandsWithLowestTotalPrice()).willReturn(List.of(mock(BrandWithTotalPriceDto.class)));

        // When
        List<BrandWithTotalPriceDto> result = sut.findBrandsWithLowestTotalPrice();

        // Then
        assertThat(result).isNotEmpty();
        then(productJpaRepository).should().findBrandsWithLowestTotalPrice();
    }

    @DisplayName("카테고리 ID로 상품 목록을 조회할 수 있다")
    @Test
    void givenCategoryId_whenFindingProducts_thenReturnsProductList() {
        // Given
        given(productJpaRepository.findProductsByCategoryId(1L)).willReturn(List.of(mock(Product.class)));

        // When
        List<Product> result = sut.findProductsByCategoryId(1L);

        // Then
        assertThat(result).isNotEmpty();
        then(productJpaRepository).should().findProductsByCategoryId(1L);
    }

    @DisplayName("브랜드 ID로 상품 목록을 조회할 수 있다")
    @Test
    void givenBrandId_whenFindingProducts_thenReturnsProductList() {
        // Given
        given(productJpaRepository.findByBrandId(1L)).willReturn(List.of(mock(Product.class)));

        // When
        List<Product> result = sut.findByBrandId(1L);

        // Then
        assertThat(result).isNotEmpty();
        then(productJpaRepository).should().findByBrandId(1L);
    }
}