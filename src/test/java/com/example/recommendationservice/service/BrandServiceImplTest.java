package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.model.BrandModel;
import com.example.recommendationservice.exception.BaseException;
import com.example.recommendationservice.exception.ErrorCode;
import com.example.recommendationservice.repository.BrandJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 브랜드")
@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {

    @InjectMocks
    private BrandServiceImpl sut;

    @Mock
    private BrandJpaRepository brandJpaRepository;

    @DisplayName("브랜드를 정상적으로 생성할 수 있다")
    @Test
    void givenValidBrandModel_whenCreatingBrand_thenSavesBrand() {
        // Given
        BrandModel brandModel = createBrandModel("브랜드A");
        given(brandJpaRepository.findByName(brandModel.getBrandName())).willReturn(Optional.empty());
        given(brandJpaRepository.save(any())).willReturn(brandModel.toEntity());

        // When & Then
        assertThatCode(() -> sut.createBrand(brandModel)).doesNotThrowAnyException();

        then(brandJpaRepository).should().findByName(brandModel.getBrandName());
        then(brandJpaRepository).should().save(any(Brand.class));
    }

    @DisplayName("이미 존재하는 브랜드 이름이면 예외가 발생한다")
    @Test
    void givenDuplicateBrandName_whenCreatingBrand_thenThrowsException() {
        // Given
        BrandModel brandModel = createBrandModel("브랜드A");
        given(brandJpaRepository.findByName(brandModel.getBrandName())).willReturn(Optional.of(Brand.of("브랜드A")));

        // When & Then
        assertThatThrownBy(() -> sut.createBrand(brandModel))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining("해당 이름의 브랜드는 이미 존재합니다.")
                .extracting("errorCode")
                .isEqualTo(ErrorCode.DUPLICATE_BRAND_NAME);

        then(brandJpaRepository).should().findByName(brandModel.getBrandName());
        then(brandJpaRepository).should(never()).save(any());
    }

    @DisplayName("존재하는 브랜드를 수정할 수 있다")
    @Test
    void givenBrandModel_whenUpdatingBrand_thenUpdatesBrand() {
        // Given
        Long brandId = 1L;
        Brand brand = createBrand("브랜드A");
        BrandModel updateModel = createBrandModel("브랜드B");
        given(brandJpaRepository.getReferenceById(brandId)).willReturn(brand);

        // When
        sut.updateBrand(brandId, updateModel);

        // Then
        assertThat(brand.getName()).isEqualTo("브랜드B");
        then(brandJpaRepository).should().getReferenceById(brandId);
    }

    @DisplayName("수정하려는 브랜드가 존재하지 않으면 예외를 발생시킨다")
    @Test
    void givenInvalidBrandId_whenUpdatingBrand_thenThrowsException() {
        // Given
        Long brandId = 999L;
        BrandModel updateModel = createBrandModel("브랜드B");
        given(brandJpaRepository.getReferenceById(brandId)).willThrow(EntityNotFoundException.class);

        // When & Then
        assertThatThrownBy(() -> sut.updateBrand(brandId, updateModel))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining("브랜드가 존재하지 않습니다.")
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NOT_EXIST_BRAND);
    }

    @DisplayName("존재하는 브랜드를 삭제할 수 있다")
    @Test
    void givenValidBrandId_whenDeletingBrand_thenDeletesBrand() {
        // Given
        Long brandId = 1L;
        willDoNothing().given(brandJpaRepository).deleteById(brandId);

        // When
        sut.deleteBrand(brandId);

        // Then
        then(brandJpaRepository).should().deleteById(brandId);
    }


    @DisplayName("브랜드 ID로 브랜드를 조회할 수 있다")
    @Test
    void givenBrandId_whenFindingBrand_thenReturnsBrand() {
        // Given
        Long brandId = 1L;
        Brand brand = createBrand("브랜드A");
        given(brandJpaRepository.findById(brandId)).willReturn(Optional.of(brand));

        // When
        Brand result = sut.findById(brandId);

        // Then
        assertThat(result).isEqualTo(brand);
        then(brandJpaRepository).should().findById(brandId);
    }

    @DisplayName("존재하지 않는 브랜드 ID로 조회 시 예외가 발생한다")
    @Test
    void givenInvalidBrandId_whenFindingBrand_thenThrowsException() {
        // Given
        Long brandId = 999L;
        given(brandJpaRepository.findById(brandId)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> sut.findById(brandId))
                .isInstanceOf(BaseException.class)
                .hasMessageContaining("브랜드가 존재하지 않습니다.")
                .extracting("errorCode")
                .isEqualTo(ErrorCode.NOT_EXIST_BRAND);
    }

    public Brand createBrand(String name) {
        return Brand.of(name);
    }

    public BrandModel createBrandModel(String name) {
        return BrandModel.of(name);
    }
}
