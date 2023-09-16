package com.example.msdealer.service.impl;

import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.exception.handler.SuccessDetails;
import com.example.msdealer.mapper.ProductMapper;
import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.entity.ProductEntity;
import com.example.msdealer.exception.ResourceNotFoundException;
import com.example.msdealer.repository.ProductRepository;
import com.example.msdealer.service.ProductForCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductForCustomerServiceImpl implements ProductForCustomerService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    @Override
    public ProductResponseDto getProductById(Long id) throws ResourceNotFoundException {
        if(productRepository.existsById(id)) {
            return productMapper.toDto(productRepository.getById(id));
        }    else {
            throw  new ResourceNotFoundException("Product not found");
        }
    }

    @Override
    public void updateStock(Long id, int quantity) throws ResourceNotFoundException, MethodArgumentNotValidException {
        if(productRepository.existsById(id)){
            ProductEntity productEntity = productRepository.getById(id);
            if(productEntity.getQuantity()>=quantity){
                productEntity.setQuantity(productEntity.getQuantity()-quantity);
            }else{
                throw new MethodArgumentNotValidException("Not enough stock");
            }

        }else {
            throw  new ResourceNotFoundException("Product not found");
        }

    }

    @Override
    public Double getPrice(Long id) throws ResourceNotFoundException {
        if(productRepository.existsById(id)){
            ProductEntity productEntity = productRepository.getById(id);
            return productEntity.getPrice();
        }else {
            throw  new ResourceNotFoundException("Product not found");
        }
    }
}
