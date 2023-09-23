package com.example.msdealer.service.impl;

import com.example.msdealer.dto.request.PageSizeDto;
import com.example.msdealer.dto.request.ProductFilterRequestDto;
import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.mapper.ProductMapper;
import com.example.msdealer.dto.request.ProductRequestDto;
import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.EmployeEntity;
import com.example.msdealer.entity.ProductEntity;
import com.example.msdealer.repository.*;
import com.example.msdealer.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductCategoryRepository productCategoryRepository;
    private final DealerRepository dealerRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductSearchCriteriaRepository productSearchCriteriaRepository;


    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public List<ProductResponseDto> getAllProducts(PageSizeDto pageSizeDto, ProductFilterRequestDto filterRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Page<ProductEntity> productEntityPage = productSearchCriteriaRepository.findAllWithFilters(pageSizeDto, filterRequestDto);
        if (dealerRepository.existsByEmail(userDetails.getUsername())) {
            logger.info("Fetching products for dealer: {}", userDetails.getUsername());
            List<ProductEntity> productEntityList =  productEntityPage.stream().filter(n->n.getDealerEntity() ==dealerRepository.getDealerEntityByEmail(userDetails.getUsername())).collect(Collectors.toList());
         return productMapper.toDtoList(productEntityList);
        } else {

            logger.info("Fetching products for employee: {}", userDetails.getUsername());
            List<ProductEntity> productEntityList =  productEntityPage.stream().filter(n->n.getDealerEntity() ==(employeeRepository.findEmployeEntityByEmail(userDetails.getUsername())).getDealerEntity()).collect(Collectors.toList());
            return productMapper.toDtoList(productEntityList);
        }
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        logger.info("Fetching product by ID: {}", id);
        return productMapper.toDto(productRepository.getById(id));
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
            productEntity.setProductCategoryEntity(productCategoryRepository.getById(productRequestDto.getCategory_id()));
            productEntity.setDealerEntity(dealerEntity);
            ProductEntity productEntitysaved = productRepository.save(productEntity);
            logger.info("Product created successfully with ID: {}", productEntitysaved.getId());

        } else {
            logger.info("Fetching products for employee: {}", userDetails.getUsername());
            DealerEntity dealerEntity=  employeeRepository.findEmployeEntityByEmail(userDetails.getUsername()).getDealerEntity();
            ProductEntity productEntity = productMapper.fromDto(productRequestDto);
            productEntity.setProductCategoryEntity(productCategoryRepository.getById(productRequestDto.getCategory_id()));
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

    @Override
    public void bulkInsertProducts(MultipartFile file) throws IOException {
        List<ProductEntity> products = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream); // Use XSSFWorkbook for .xlsx files
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Skip the header row
                    continue;
                }
                ProductRequestDto requestDto = new ProductRequestDto();
                requestDto.setName(row.getCell(0).getStringCellValue());
                requestDto.setBrand(row.getCell(1).getStringCellValue());
                requestDto.setDescription(row.getCell(2).getStringCellValue());
                requestDto.setQuantity((int) row.getCell(3).getNumericCellValue());
                requestDto.setAmount(row.getCell(4).getNumericCellValue());

                ProductEntity productEntity = productMapper.fromDto(requestDto);
                productEntity.setProductCategoryEntity(productCategoryRepository.getById((long) row.getCell(5).getNumericCellValue()));
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                if (dealerRepository.existsByEmail(userDetails.getUsername())) {
                    DealerEntity dealerEntity=  dealerRepository.getDealerEntityByEmail(userDetails.getUsername());
                    productEntity.setDealerEntity(dealerEntity);
                    ProductEntity productEntitysaved = productRepository.save(productEntity);
                    logger.info("Product created successfully with ID: {}", productEntitysaved.getId());

                } else {
                    logger.info("Fetching products for employee: {}", userDetails.getUsername());
                    DealerEntity dealerEntity=  employeeRepository.findEmployeEntityByEmail(userDetails.getUsername()).getDealerEntity();
                    productEntity.setDealerEntity(dealerEntity);
                    ProductEntity productEntitysaved = productRepository.save(productEntity);
                    logger.info("Product created successfully with ID: {}", productEntitysaved.getId());
                }
                products.add(productEntity);
            }

            workbook.close();
        }

         productRepository.saveAll(products);
    }

}

