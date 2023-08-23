package com.example.msdealer.dto.mapper;

import com.example.msdealer.dto.request.ProductCategoryRequsetDto;
import com.example.msdealer.dto.request.ProductRequestDto;
import com.example.msdealer.dto.response.ProductCategoryResponseDto;
import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.entity.ProductCategoryEntity;
import com.example.msdealer.entity.ProductEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper
public class ProductMapper {

    public ProductEntity fromDto(ProductRequestDto productRequestDto){
     return    ProductEntity.builder()
                .amount(productRequestDto.getAmount())
                .brand(productRequestDto.getBrand())
                .description(productRequestDto.getDescription())
                .name(productRequestDto.getName())
                .quantity(productRequestDto.getQuantity())
                .build();


    }

    public ProductResponseDto toDto(ProductEntity entity){
      return   ProductResponseDto.builder()
                .amount(entity.getAmount())
                .brand(entity.getBrand())
                .description(entity.getDescription())
                .name(entity.getName())
                .quantity(entity.getQuantity())
                .category_id(entity.getProductCategoryEntity().getId())
                .dealer_id(entity.getDealerEntity().getId())
              .createdBy(entity.getCreatedBy())
              .createdDate(entity.getCreatedDate())
              .lastModifiedBy(entity.getLastModifiedBy())
              .lastModifiedDate(entity.getLastModifiedDate())
                .build();

    }

    public List<ProductResponseDto> toDTOList(List<ProductEntity> productEntityList){
        ProductMapper productMapper = new ProductMapper();

       return productEntityList.stream().map(productMapper::toDto).collect(Collectors.toList());
    }

    public List<ProductEntity> fromDTOList(List<ProductRequestDto> productRequestDtoList){
        ProductMapper productMapper = new ProductMapper();

        return productRequestDtoList.stream().map(productMapper::fromDto).collect(Collectors.toList());

    }
}
