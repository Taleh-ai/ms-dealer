package com.example.msdealer.service.impl;

import com.example.msdealer.mapper.ProductMapper;
import com.example.msdealer.dto.request.ProductRequestDto;
import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.EmployeEntity;
import com.example.msdealer.entity.ProductEntity;
import com.example.msdealer.repository.DealerRepository;
import com.example.msdealer.repository.EmployeeRepository;
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
    private final EmployeeRepository employeeRepository;
    @Override
    public List<ProductEntity> getAllProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if(dealerRepository.existsByEmail(userDetails.getUsername())){
                return productRepository.findAllByDealerEntity(dealerRepository.findDealerEntityByEmail(userDetails.getUsername()));
            }else {
                return productRepository.findAllByDealerEntity((employeeRepository.findEmployeEntityByEmail(userDetails.getUsername())).getDealerEntity());
            }
    }

    @Override
    public ProductEntity getProductById(Long id) {
        return productRepository.getById(id);
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
        productEntity.setPrice(productRequestDto.getAmount());
        productEntity.setName(productRequestDto.getName());
        productEntity.setBrand(productRequestDto.getBrand());
        productEntity.setDescription(productRequestDto.getDescription());
        productEntity.setQuantity(productRequestDto.getQuantity());
        productRepository.save(productEntity);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
