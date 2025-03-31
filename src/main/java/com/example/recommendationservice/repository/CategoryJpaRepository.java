package com.example.recommendationservice.repository;

import com.example.recommendationservice.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String categoryName);
}
