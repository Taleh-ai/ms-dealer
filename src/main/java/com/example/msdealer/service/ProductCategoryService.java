package com.example.msdealer.service;

import com.example.msdealer.dto.request.ProductCategoryRequsetDto;
import com.example.msdealer.dto.response.ProductCategoryResponseDto;

import java.util.List;

public interface ProductCategoryService {
    public List<ProductCategoryResponseDto> getAllProductCategories();
    public ProductCategoryResponseDto getProductCategoryById(Long id);
    public void createProductCategory(ProductCategoryRequsetDto productCategory);
    public void updateProductCategory(Long id,ProductCategoryRequsetDto productCategory);


}
