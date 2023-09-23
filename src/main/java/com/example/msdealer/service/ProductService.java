package com.example.msdealer.service;

import com.example.msdealer.dto.request.PageSizeDto;
import com.example.msdealer.dto.request.ProductFilterRequestDto;
import com.example.msdealer.dto.request.ProductRequestDto;
import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.entity.ProductEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public List<ProductResponseDto> getAllProducts(PageSizeDto pageSizeDto, ProductFilterRequestDto filterRequestDto);
    public ProductResponseDto getProductById(Long id);
    public void createProduct(ProductRequestDto productRequestDto);
    public void updateProduct(Long id,ProductRequestDto productRequestDto);
    public void deleteProduct(Long id);
    public void bulkInsertProducts(MultipartFile file) throws IOException;
}
