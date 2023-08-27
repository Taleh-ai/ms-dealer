package com.example.msdealer.service;

import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.exception.ResourceNotFoundException;

import java.util.List;

public interface ProductForCustomerService {
    public List<ProductResponseDto> getAllProducts();
    public ProductResponseDto getProductById(Long id) throws ResourceNotFoundException;

}
