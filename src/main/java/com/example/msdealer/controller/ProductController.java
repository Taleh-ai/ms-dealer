package com.example.msdealer.controller;

import com.example.msdealer.dto.request.ProductRequestDto;
import com.example.msdealer.entity.ProductEntity;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.exception.handler.SuccessDetails;
import com.example.msdealer.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {
    private final ProductServiceImpl productService;

    @PutMapping("product")
    public ResponseEntity<SuccessDetails<String>> addProductCategory(@RequestBody ProductRequestDto productRequestDto) throws MethodArgumentNotValidException {
        productService.createProduct(productRequestDto);
        return ResponseEntity.ok(new SuccessDetails<>("Product created Successfully!", HttpStatus.OK.value(),true));
    }
    @PostMapping("product/{id}")
    public ResponseEntity<SuccessDetails<String>> updateProductCategory(@PathVariable(name = "id") Long id,
                                                                        @RequestBody ProductRequestDto productRequestDto)
            throws MethodArgumentNotValidException {
        productService.updateProduct(id,productRequestDto);
        return ResponseEntity.ok(new SuccessDetails<>("Product  updated Successfully!", HttpStatus.OK.value(),true));
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<SuccessDetails<String>> deleteProduct(@PathVariable(name = "id") Long id) throws MethodArgumentNotValidException {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new SuccessDetails<>("Product  deleted  Successfully!", HttpStatus.OK.value(),true));
    }
    @GetMapping("product/{id}")
    public ResponseEntity<SuccessDetails<ProductEntity>> getProduct(@PathVariable(name = "id") Long id) throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(productService.getProductById(id),HttpStatus.OK.value(),true));
    }

    @GetMapping("product")
    public ResponseEntity<SuccessDetails<List<ProductEntity>>> getProducts() throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(productService.getAllProducts(),HttpStatus.OK.value(),true));
    }
}
