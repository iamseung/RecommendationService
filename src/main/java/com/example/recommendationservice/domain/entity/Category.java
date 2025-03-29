package com.example.recommendationservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@Table(name = "category")
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 예: 상의, 아우터 ...
}
