package com.example.msdealer.repository;

import com.example.msdealer.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity,Long> {
    boolean existsByCategoryIgnoreCase(String category);
}
