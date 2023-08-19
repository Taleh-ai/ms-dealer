package com.example.msdealer.service;

import com.example.msdealer.dto.request.ProductCategoryRequsetDto;
import com.example.msdealer.dto.request.ProductRequestDto;
import com.example.msdealer.dto.response.ProductCategoryResponseDto;
import com.example.msdealer.dto.response.ProductResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    public List<ProductResponseDto> getAllProducts();
    public ProductResponseDto getProductById(Long id);
    public void createProduct(ProductRequestDto productRequestDto);
    public void updateProduct(Long id,ProductRequestDto productRequestDto);
}
