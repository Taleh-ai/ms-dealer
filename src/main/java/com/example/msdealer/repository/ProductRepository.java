package com.example.msdealer.repository;

import com.example.msdealer.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
}
