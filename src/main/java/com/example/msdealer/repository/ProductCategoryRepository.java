package com.example.msdealer.repository;

import com.example.msdealer.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity,Long> {
}
