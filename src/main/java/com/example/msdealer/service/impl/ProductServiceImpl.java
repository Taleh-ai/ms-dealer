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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public List<ProductEntity> getAllProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (dealerRepository.existsByEmail(userDetails.getUsername())) {
            logger.info("Fetching products for dealer: {}", userDetails.getUsername());
            return productRepository.findAllByDealerEntity(dealerRepository.getDealerEntityByEmail(userDetails.getUsername()));
        } else {
            logger.info("Fetching products for employee: {}", userDetails.getUsername());
            return productRepository.findAllByDealerEntity((employeeRepository.findEmployeEntityByEmail(userDetails.getUsername())).getDealerEntity());
        }
    }

    @Override
    public ProductEntity getProductById(Long id) {
        logger.info("Fetching product by ID: {}", id);
        return productRepository.getById(id);
    }

    @Override
    public void createProduct(ProductRequestDto productRequestDto) {
        logger.info("Creating a new product: {}", productRequestDto.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (dealerRepository.existsByEmail(userDetails.getUsername())) {
            logger.info("Fetching products for dealer: {}", userDetails.getUsername());
            DealerEntity dealerEntity=  dealerRepository.getDealerEntityByEmail(userDetails.getUsername());
            ProductEntity productEntity = productMapper.fromDto(productRequestDto);
            productEntity.setDealerEntity(dealerEntity);
            ProductEntity productEntitysaved = productRepository.save(productEntity);
            logger.info("Product created successfully with ID: {}", productEntitysaved.getId());

        } else {
            logger.info("Fetching products for employee: {}", userDetails.getUsername());
            DealerEntity dealerEntity=  employeeRepository.findEmployeEntityByEmail(userDetails.getUsername()).getDealerEntity();
            ProductEntity productEntity = productMapper.fromDto(productRequestDto);
            productEntity.setDealerEntity(dealerEntity);
            ProductEntity productEntitysaved = productRepository.save(productEntity);
            logger.info("Product created successfully with ID: {}", productEntitysaved.getId());
        }
    }

    @Override
    public void updateProduct(Long id, ProductRequestDto productRequestDto) {
        logger.info("Updating product with ID: {}", id);
        ProductEntity productEntity = productRepository.getById(id);
        productEntity.setProductCategoryEntity(productCategoryRepository.getById(productRequestDto.getCategory_id()));
        productEntity.setDealerEntity(dealerRepository.getById(productRequestDto.getDealer_id()));
        productEntity.setPrice(productRequestDto.getAmount());
        productEntity.setName(productRequestDto.getName());
        productEntity.setBrand(productRequestDto.getBrand());
        productEntity.setDescription(productRequestDto.getDescription());
        productEntity.setQuantity(productRequestDto.getQuantity());
        productRepository.save(productEntity);
        logger.info("Product updated successfully.");
    }

    @Override
    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
        logger.info("Product deleted successfully.");
    }
}

