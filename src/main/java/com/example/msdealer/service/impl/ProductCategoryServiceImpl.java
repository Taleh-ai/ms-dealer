package com.example.msdealer.service.impl;

import com.example.msdealer.mapper.ProductCategoryMapper;
import com.example.msdealer.dto.request.ProductCategoryRequsetDto;
import com.example.msdealer.dto.response.ProductCategoryResponseDto;
import com.example.msdealer.entity.ProductCategoryEntity;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.repository.ProductCategoryRepository;
import com.example.msdealer.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategoryResponseDto> getAllProductCategories() {
        logger.info("Fetching all product categories.");
        List<ProductCategoryResponseDto> categories = productCategoryMapper.toDtoList(productCategoryRepository.findAll());
        logger.info("Fetched {} product categories.", categories.size());
        return categories;
    }

    @Override
    public ProductCategoryResponseDto getProductCategoryById(Long id) {
        logger.info("Fetching product category by ID: {}", id);
        ProductCategoryResponseDto category = productCategoryMapper.toDto(productCategoryRepository.getById(id));
        logger.info("Fetched product category: {}", category);
        return category;
    }

    @Override
    public void createProductCategory(ProductCategoryRequsetDto productCategory) throws MethodArgumentNotValidException {
        logger.info("Creating a new product category: {}", productCategory.getCategory());
        if (productCategoryRepository.existsByCategoryIgnoreCase(productCategory.getCategory())) {
            logger.warn("Category already exists: {}", productCategory.getCategory());
            throw new MethodArgumentNotValidException("Category already exists");
        }
        ProductCategoryEntity productCategoryEntity = productCategoryMapper.fromDto(productCategory);
        productCategoryRepository.save(productCategoryEntity);
        logger.info("Created product category: {}", productCategoryEntity);
    }

    @Override
    public void updateProductCategory(Long id, ProductCategoryRequsetDto productCategory) {
        logger.info("Updating product category with ID {}: {}", id, productCategory.getCategory());
        ProductCategoryEntity productCategoryEntity = productCategoryRepository.getById(id);
        productCategoryEntity.setCategory(productCategory.getCategory());
        productCategoryRepository.save(productCategoryEntity);
        logger.info("Updated product category: {}", productCategoryEntity);
    }

    @Override
    public void deleteProductCategory(Long id) {
        logger.info("Deleting product category with ID: {}", id);
        productCategoryRepository.deleteById(id);
        logger.info("Deleted product category with ID: {}", id);
    }
}
