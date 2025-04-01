package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.exception.BaseException;
import com.example.recommendationservice.exception.ErrorCode;
import com.example.recommendationservice.repository.CategoryJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("카테고리 서비스")
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks private CategoryServiceImpl sut;
    @Mock private CategoryJpaRepository categoryJpaRepository;

    @DisplayName("전체 카테고리를 조회할 수 있다")
    @Test
    void givenNothing_whenFindingAllCategories_thenReturnsCategoryList() {
        // Given
        List<Category> expected = List.of(
                createCategory("상의"),
                createCategory("바지")
        );

        given(categoryJpaRepository.findAll()).willReturn(expected);

        // When
        List<Category> result = sut.findAllCategories();

        // Then
        assertThat(result).hasSize(2).containsAll(expected);
        then(categoryJpaRepository).should().findAll();
    }

    @DisplayName("카테고리 이름으로 조회할 수 있다")
    @Test
    void givenCategoryName_whenFindingCategory_thenReturnsCategory() {
        // Given
        String categoryName = "상의";
        Category expected = createCategory(categoryName);
        given(categoryJpaRepository.findByName(categoryName)).willReturn(Optional.of(expected));

        // When
        Category result = sut.findByName(categoryName);

        // Then
        assertThat(result.getName()).isEqualTo(categoryName);
        then(categoryJpaRepository).should().findByName(categoryName);
    }

    @DisplayName("존재하지 않는 이름으로 조회하면 예외가 발생한다")
    @Test
    void givenInvalidName_whenFindingCategory_thenThrowsException() {
        // Given
        String invalidName = "모자123";
        given(categoryJpaRepository.findByName(invalidName)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> sut.findByName(invalidName))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining("카테고리가 존재하지 않습니다")
                .extracting("errorCode").isEqualTo(ErrorCode.NOT_EXIST_CATEGORY);

        then(categoryJpaRepository).should().findByName(invalidName);
    }

    @DisplayName("카테고리 ID로 조회할 수 있다")
    @Test
    void givenCategoryId_whenFindingCategory_thenReturnsCategory() {
        // Given
        Long categoryId = 1L;
        Category expected = createCategory("아우터");
        given(categoryJpaRepository.findById(categoryId)).willReturn(Optional.of(expected));

        // When
        Category result = sut.findById(categoryId);

        // Then
        assertThat(result).isEqualTo(expected);
        then(categoryJpaRepository).should().findById(categoryId);
    }

    @DisplayName("존재하지 않는 ID로 조회하면 예외가 발생한다")
    @Test
    void givenInvalidId_whenFindingCategory_thenThrowsException() {
        // Given
        Long invalidId = 999L;
        given(categoryJpaRepository.findById(invalidId)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> sut.findById(invalidId))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining("카테고리가 존재하지 않습니다")
                .extracting("errorCode").isEqualTo(ErrorCode.NOT_EXIST_CATEGORY);

        then(categoryJpaRepository).should().findById(invalidId);
    }

    public Category createCategory(String name) {
        return Category.of(name);
    }
}
