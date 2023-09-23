package com.example.msdealer.controller;

import com.example.msdealer.dto.request.PageSizeDto;
import com.example.msdealer.dto.request.ProductFilterRequestDto;
import com.example.msdealer.dto.request.ProductRequestDto;
import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.entity.ProductEntity;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.exception.handler.SuccessDetails;
import com.example.msdealer.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/product")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {
    private final ProductServiceImpl productService;

    @PostMapping
    public ResponseEntity<SuccessDetails<String>> addProductCategory(@Valid @RequestBody ProductRequestDto productRequestDto) throws MethodArgumentNotValidException {
        productService.createProduct(productRequestDto);
        return ResponseEntity.ok(new SuccessDetails<>("Product created Successfully!", HttpStatus.OK.value(), true));
    }

    @PutMapping("{id}")
    public ResponseEntity<SuccessDetails<String>> updateProductCategory(@PathVariable Long id, @Valid @RequestBody ProductRequestDto productRequestDto) throws MethodArgumentNotValidException {
        productService.updateProduct(id, productRequestDto);
        return ResponseEntity.ok(new SuccessDetails<>("Product updated Successfully!", HttpStatus.OK.value(), true));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<SuccessDetails<String>> deleteProduct(@PathVariable(name = "id") Long id) throws MethodArgumentNotValidException {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new SuccessDetails<>("Product  deleted  Successfully!", HttpStatus.OK.value(), true));
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessDetails<ProductResponseDto>> getProduct(@PathVariable(name = "id") Long id) throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(productService.getProductById(id), HttpStatus.OK.value(), true));
    }

    @GetMapping
    public ResponseEntity<SuccessDetails<List<ProductResponseDto>>> getProducts(@ModelAttribute PageSizeDto pageSizeDto,
                                                                                @RequestParam(name = "maxQuantity", required = false) Integer maxQuantity,
                                                                                @RequestParam(name = "minQuantity", required = false) Integer minQuantity,
                                                                                @RequestParam(name = "minAmount", required = false) Double minAmount,
                                                                                @RequestParam(name = "maxAmount", required = false) Double maxAmount) {
        ProductFilterRequestDto filterRequestDto = new ProductFilterRequestDto(minQuantity,maxQuantity,minAmount,maxAmount);

        return ResponseEntity.ok(new SuccessDetails<>(productService.getAllProducts(pageSizeDto, filterRequestDto), HttpStatus.OK.value(), true));

    }
    @PostMapping(path = "/bulk-insert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessDetails<String>> addProductBulk( @RequestPart("file") MultipartFile file) throws MethodArgumentNotValidException, IOException {
        productService.bulkInsertProducts(file);
        return ResponseEntity.ok(new SuccessDetails<>("Products inserted Successfully!", HttpStatus.OK.value(), true));
    }

}
