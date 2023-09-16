package com.example.msdealer.service;

import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.exception.ResourceNotFoundException;
import com.example.msdealer.exception.handler.SuccessDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductForCustomerService {
    public List<ProductResponseDto> getAllProducts();
    public ProductResponseDto getProductById(Long id) throws ResourceNotFoundException;
    public void updateStock(Long id, int quantity) throws ResourceNotFoundException, MethodArgumentNotValidException;

    public Double getPrice(Long id) throws ResourceNotFoundException;

}
