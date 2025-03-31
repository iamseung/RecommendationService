package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.dto.response.BrandWithTotalPriceDto;
import com.example.recommendationservice.domain.entity.Brand;
import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.domain.entity.Product;
import com.example.recommendationservice.domain.model.ProductModel;
import com.example.recommendationservice.exception.ErrorCode;
import com.example.recommendationservice.exception.BaseException;
import com.example.recommendationservice.repository.ProductJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductJpaRepository productJpaRepository;

    @Transactional
    @Override
    public void createProduct(ProductModel productModel) {
        try {
            Brand brand = brandService.findById(productModel.getBrandId());
            Category category = categoryService.findById(productModel.getCategoryId());

            Product product = Product.of(brand, category, productModel.getPrice());
            productJpaRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new BaseException(ErrorCode.DUPLICATE_PRODUCT, "해당 브랜드-카테고리 조합의 상품이 이미 존재합니다.");
        }
    }

    @Transactional
    @Override
    public void updateProduct(Long productId, ProductModel productModel) {
        try {
            Product product = productJpaRepository.getReferenceById(productId);

            Brand brand = (productModel.getBrandId() != null) ? brandService.findById(productModel.getBrandId()) : null;
            Category category = (productModel.getCategoryId() != null) ? categoryService.findById(productModel.getCategoryId()) : null;
            Integer price = (productModel.getPrice() != null) ? productModel.getPrice() : null;

            product.update(brand, category, price);
        } catch (EntityNotFoundException e) {
            throw new BaseException(ErrorCode.NOT_EXIST_PRODUCT, "상품이 존재하지 않습니다. productId : %s".formatted(productId));
        }
    }

    @Transactional
    @Override
    public void deleteProduct(Long productId) {
        productJpaRepository.deleteById(productId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findMinPriceProductsByCategory() {
        return productJpaRepository.findMinPriceProductsByCategory();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BrandWithTotalPriceDto> findBrandsWithLowestTotalPrice() {
        return productJpaRepository.findBrandsWithLowestTotalPrice();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findProductsByCategoryId(Long categoryId) {
        return productJpaRepository.findProductsByCategoryId(categoryId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findByBrandId(Long brandId) {
        return productJpaRepository.findByBrandId(brandId);
    }
}
