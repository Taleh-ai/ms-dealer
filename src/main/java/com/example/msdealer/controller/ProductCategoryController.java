package com.example.msdealer.controller;

import com.example.msdealer.dto.request.ProductCategoryRequsetDto;
import com.example.msdealer.dto.response.ProductCategoryResponseDto;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.exception.handler.SuccessDetails;
import com.example.msdealer.service.impl.ProductCategoryServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/product-category")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductCategoryController {
private final ProductCategoryServiceImpl productCategoryService;


    @PostMapping
    public ResponseEntity<SuccessDetails<String>> addProductCategory(@Valid @RequestBody ProductCategoryRequsetDto productCategoryRequsetDto) throws MethodArgumentNotValidException {
        productCategoryService.createProductCategory(productCategoryRequsetDto);
        return ResponseEntity.ok(new SuccessDetails<>("Product category created Successfully!", HttpStatus.OK.value(),true));
    }
    @PutMapping("{id}")
    public ResponseEntity<SuccessDetails<String>> updateProductCategory(@PathVariable(name = "id") Long id,
                                                                        @Valid @RequestBody ProductCategoryRequsetDto productCategoryRequsetDto)
            throws MethodArgumentNotValidException {
        productCategoryService.updateProductCategory(id,productCategoryRequsetDto);
        return ResponseEntity.ok(new SuccessDetails<>("Product Category updated Successfully!", HttpStatus.OK.value(),true));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SuccessDetails<String>> deleteProductCategory(@PathVariable(name = "id") Long id) throws MethodArgumentNotValidException {
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.ok(new SuccessDetails<>("Product Category deleted  Successfully!", HttpStatus.OK.value(),true));
    }
    @GetMapping("{id}")
    public ResponseEntity<SuccessDetails<ProductCategoryResponseDto>> getProductCategory(@PathVariable(name = "id") Long id) throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(productCategoryService.getProductCategoryById(id),HttpStatus.OK.value(),true));
    }

    @GetMapping
    public ResponseEntity<SuccessDetails<List<ProductCategoryResponseDto>>> getProductCategories() throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(productCategoryService.getAllProductCategories(),HttpStatus.OK.value(),true));
    }
}
