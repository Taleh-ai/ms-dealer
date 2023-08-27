package com.example.msdealer.service.impl;

import com.example.msdealer.dto.mapper.ProductCategoryMapper;
import com.example.msdealer.dto.request.ProductCategoryRequsetDto;
import com.example.msdealer.dto.response.ProductCategoryResponseDto;
import com.example.msdealer.entity.ProductCategoryEntity;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.repository.ProductCategoryRepository;
import com.example.msdealer.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;
    @Override
    public List<ProductCategoryResponseDto> getAllProductCategories() {

        return   productCategoryMapper.toDtoList(productCategoryRepository.findAll());
    }

    @Override
    public ProductCategoryResponseDto getProductCategoryById(Long id) {
        return productCategoryMapper.toDto(productCategoryRepository.getById(id));
    }

    @Override
    public void createProductCategory(ProductCategoryRequsetDto productCategory) throws MethodArgumentNotValidException {
        if(productCategoryRepository.existsByCategoryIgnoreCase(productCategory.getCategory())){
            throw new MethodArgumentNotValidException("Category already exist");
        }
        ProductCategoryEntity productCategoryEntity = productCategoryMapper.fromDto(productCategory);
        productCategoryRepository.save(productCategoryEntity);
    }

    @Override
    public void updateProductCategory(Long id, ProductCategoryRequsetDto productCategory) {
        ProductCategoryEntity productCategoryEntity = productCategoryRepository.getById(id);
        productCategoryEntity.setCategory(productCategory.getCategory());
        productCategoryRepository.save(productCategoryEntity);
    }

    @Override
    public void deleteProductCategory(Long id) {
        productCategoryRepository.deleteById(id);
    }
}
