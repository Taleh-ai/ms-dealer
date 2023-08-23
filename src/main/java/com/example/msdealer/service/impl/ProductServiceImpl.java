package com.example.msdealer.service.impl;

import com.example.msdealer.dto.mapper.ProductMapper;
import com.example.msdealer.dto.request.ProductRequestDto;
import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.EmployeEntity;
import com.example.msdealer.entity.ProductEntity;
import com.example.msdealer.repository.DealerRepository;
import com.example.msdealer.repository.ProductCategoryRepository;
import com.example.msdealer.repository.ProductRepository;
import com.example.msdealer.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductCategoryRepository productCategoryRepository;
    private final DealerRepository dealerRepository;
    @Override
    public List<ProductResponseDto> getAllProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if((DealerEntity)userDetails instanceof DealerEntity ){
                return productMapper.toDTOList(productRepository.findAllByDealerEntity((DealerEntity)userDetails));
            }else {
                return productMapper.toDTOList(productRepository.findAllByDealerEntity(((EmployeEntity)userDetails).getDealerEntity()));
            }
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        return productMapper.toDto(productRepository.getById(id));
    }

    @Override
    public void createProduct(ProductRequestDto productRequestDto) {
        ProductEntity productEntity = productMapper.fromDto(productRequestDto);
        productRepository.save(productEntity);
    }

    @Override
    public void updateProduct(Long id, ProductRequestDto productRequestDto) {
        ProductEntity productEntity = productRepository.getById(id);
        productEntity.setProductCategoryEntity(productCategoryRepository.getById(productRequestDto.getCategory_id()));
        productEntity.setDealerEntity(dealerRepository.getById(productRequestDto.getDealer_id()));
        productEntity.setAmount(productRequestDto.getAmount());
        productEntity.setName(productRequestDto.getName());
        productEntity.setBrand(productRequestDto.getBrand());
        productEntity.setDescription(productRequestDto.getDescription());
        productEntity.setQuantity(productRequestDto.getQuantity());
    }
}
