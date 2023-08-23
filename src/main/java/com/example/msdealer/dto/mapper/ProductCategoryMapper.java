package com.example.msdealer.dto.mapper;


import com.example.msdealer.dto.request.ProductCategoryRequsetDto;
import com.example.msdealer.dto.response.ProductCategoryResponseDto;
import com.example.msdealer.entity.ProductCategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper
public class ProductCategoryMapper {

    public ProductCategoryEntity fromDto(ProductCategoryRequsetDto productCategoryRequestDto) {
       return ProductCategoryEntity.builder()
                .category(productCategoryRequestDto.getCategory()).build();
    }


    public ProductCategoryResponseDto toDto(ProductCategoryEntity productCategoryEntity){
        return  ProductCategoryResponseDto.builder()
                .id(productCategoryEntity.getId())
                .category(productCategoryEntity.getCategory()).build();
    }

    public List<ProductCategoryResponseDto> toDtoList(List<ProductCategoryEntity> productCategoryEntityList){
        ProductCategoryMapper productCategoryMapper = new ProductCategoryMapper();
        return productCategoryEntityList.stream().map(n->productCategoryMapper.toDto(n)).collect(Collectors.toList());
    }

    public List<ProductCategoryEntity> fromDtoList(List<ProductCategoryRequsetDto> productCategoryRequsetDtoList){
        ProductCategoryMapper productCategoryMapper = new ProductCategoryMapper();
        return productCategoryRequsetDtoList.stream().map(n->productCategoryMapper.fromDto(n)).collect(Collectors.toList());
    }
}
