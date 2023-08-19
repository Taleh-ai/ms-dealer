package com.example.msdealer.dto.mapper;


import com.example.msdealer.dto.request.ProductCategoryRequsetDto;
import com.example.msdealer.dto.response.ProductCategoryResponseDto;
import com.example.msdealer.entity.ProductCategoryEntity;
import lombok.RequiredArgsConstructor;
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
public interface ProductCategoryMapper {

    ProductCategoryEntity fromDto(ProductCategoryRequsetDto productCategoryRequsetDto);

    ProductCategoryResponseDto toDto(ProductCategoryEntity entity);

    List<ProductCategoryResponseDto> toDTOList(List<ProductCategoryEntity> productCategoryEntityList);

    List<ProductCategoryEntity> fromDTOList(List<ProductCategoryRequsetDto> dtoList);
}
