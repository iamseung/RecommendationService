package com.example.recommendationservice.repository;

import com.example.recommendationservice.domain.dto.response.BrandWithTotalPriceDto;
import com.example.recommendationservice.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT p 
        FROM Product p 
        JOIN FETCH p.brand 
        JOIN FETCH p.category 
        WHERE p.price = (
            SELECT MIN(p2.price) 
            FROM Product p2 
            WHERE p2.category = p.category
        )
    """)
    List<Product> findMinPriceProductsByCategory();

    @Query(value = """
        SELECT p.brand_id AS brandId, SUM(p.price) AS totalPrice
        FROM product p
        GROUP BY p.brand_id
        HAVING SUM(p.price) = (
            SELECT MIN(total_price)
            FROM (
                SELECT brand_id, SUM(price) AS total_price
                FROM product
                GROUP BY brand_id
            ) AS sub
        )
        """, nativeQuery = true)
    List<BrandWithTotalPriceDto> findBrandsWithLowestTotalPrice();

    List<Product> findProductsByCategoryId(Long categoryId);

    List<Product> findByBrandId(Long brandId);

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.brand.id = :brandId")
    List<Product> findByBrandIdWithCategory(@Param("brandId") Long brandId);

    @Query("SELECT p FROM Product p JOIN FETCH p.brand WHERE p.category.id = :categoryId")
    List<Product> findProductsByCategoryIdWithBrand(@Param("categoryId") Long categoryId);

}
