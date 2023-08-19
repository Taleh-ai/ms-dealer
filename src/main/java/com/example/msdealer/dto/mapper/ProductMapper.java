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

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ProductMapper {

    ProductEntity fromDto(ProductRequestDto productRequestDto);

    ProductResponseDto toDto(ProductEntity entity);

    List<ProductResponseDto> toDTOList(List<ProductEntity> productEntityList);

    List<ProductEntity> fromDTOList(List<ProductRequestDto> productRequestDtoList);
}
