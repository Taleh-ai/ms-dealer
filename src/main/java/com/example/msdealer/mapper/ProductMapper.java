package com.example.msdealer.mapper;


import com.example.msdealer.dto.request.ProductRequestDto;
import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper
public class ProductMapper {

    public ProductEntity fromDto(ProductRequestDto productRequestDto){
     return    ProductEntity.builder()
                .price(productRequestDto.getAmount())
                .brand(productRequestDto.getBrand())
                .description(productRequestDto.getDescription())
                .name(productRequestDto.getName())
                .quantity(productRequestDto.getQuantity())
                .build();


    }
    public List<ProductEntity> fromDTOList(List<ProductRequestDto> productRequestDtoList){
        ProductMapper productMapper = new ProductMapper();

        return productRequestDtoList.stream().map(productMapper::fromDto).collect(Collectors.toList());

    }


    public ProductResponseDto toDto(ProductEntity productEntity){
        ProductResponseDto productResponseDto =     ProductResponseDto.builder()
                .amount(productEntity.getPrice())
                .brand(productEntity.getBrand())
                .description(productEntity.getDescription())
                .name(productEntity.getName())
                .dealer(productEntity.getDealerEntity().getCompanyName())
                .category(productEntity.getProductCategoryEntity().getCategory())
                .dealerId(productEntity.getDealerEntity().getId())
                .id(productEntity.getId())
                .creationDate(productEntity.getCreationDate())
                .updateDate(productEntity.getUpdateDate())
                .build();

        if (productEntity.getQuantity()>=10){
            productResponseDto.setStockSituation("Has stock");
        } else if (productEntity.getQuantity()>=5) {
            productResponseDto.setStockSituation("Product stock is running out");
        } else if (productEntity.getQuantity()>=1) {
            productResponseDto.setStockSituation("Last "+productEntity.getQuantity()+" product");
        } else {
            productResponseDto.setStockSituation("No stock");
        }
        return  productResponseDto;

    }
    public List<ProductResponseDto> toDtoList(List<ProductEntity> productEntityList){
        ProductMapper productMapper = new ProductMapper();

        return productEntityList.stream().map(productMapper::toDto).collect(Collectors.toList());

    }
}
