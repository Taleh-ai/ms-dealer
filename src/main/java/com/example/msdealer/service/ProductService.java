package com.example.msdealer.service;

import com.example.msdealer.dto.request.ProductRequestDto;
import com.example.msdealer.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    public List<ProductEntity> getAllProducts();
    public ProductEntity getProductById(Long id);
    public void createProduct(ProductRequestDto productRequestDto);
    public void updateProduct(Long id,ProductRequestDto productRequestDto);
    public void deleteProduct(Long id);
}
