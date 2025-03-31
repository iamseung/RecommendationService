package com.example.recommendationservice.repository;

import com.example.recommendationservice.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandJpaRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByName(String brandName);
}
