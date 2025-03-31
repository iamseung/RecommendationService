package com.example.recommendationservice.service;

import com.example.recommendationservice.domain.entity.Category;
import com.example.recommendationservice.exception.ErrorCode;
import com.example.recommendationservice.exception.BaseException;
import com.example.recommendationservice.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Category> findAllCategories() {
        return categoryJpaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Category findByName(String categoryName) {
        return categoryJpaRepository.findByName(categoryName)
                .orElseThrow(() ->
                        new BaseException(ErrorCode.NOT_EXIST_CATEGORY, "카테고리가 존재하지 않습니다. categoryName : %s".formatted(categoryName))
                );
    }

    @Transactional(readOnly = true)
    @Override
    public Category findById(Long categoryId) {
        return categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_CATEGORY, "카테고리가 존재하지 않습니다. categoryId : %s".formatted(categoryId)));
    }
}
