package com.example.msdealer.service;

import com.example.msdealer.entity.ProductCategoryEntity;

import java.util.List;

public interface ProductCategoryService {
    public List<ProductCategoryEntity> getAllProductCategories();
    public ProductCategoryEntity getProductCategoryById(Long id);
    public ProductCategoryEntity createProductCategory(ProductCategoryEntity productCategory);


}
