package com.example.msdealer.controller;

import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.exception.ResourceNotFoundException;
import com.example.msdealer.exception.handler.SuccessDetails;
import com.example.msdealer.service.ProductForCustomerService;
import com.example.msdealer.service.impl.ProductForCustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product-feign")
@RequiredArgsConstructor
public class ProductForCustomerController {
    private final ProductForCustomerServiceImpl productForCustomerService;

    @GetMapping
    public ResponseEntity<SuccessDetails<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> products = productForCustomerService.getAllProducts();
        return ResponseEntity.ok(new SuccessDetails<>(products, HttpStatus.OK.value(), true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessDetails<ProductResponseDto>> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
        ProductResponseDto product = productForCustomerService.getProductById(id);
        return ResponseEntity.ok(new SuccessDetails<>(product, HttpStatus.OK.value(), true));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<SuccessDetails<String>> updateProductStock(@PathVariable Long id,
                                                                     @RequestParam int quantity)
            throws MethodArgumentNotValidException, ResourceNotFoundException {
        productForCustomerService.updateStock(id, quantity);
        return ResponseEntity.ok(new SuccessDetails<>("Product stock updated successfully!", HttpStatus.OK.value(), true));
    }

    @GetMapping("/{id}/price")
    public ResponseEntity<SuccessDetails<Double>> getProductPriceById(@PathVariable Long id) throws ResourceNotFoundException {
        Double price = productForCustomerService.getPrice(id);
        return ResponseEntity.ok(new SuccessDetails<>(price, HttpStatus.OK.value(), true));
    }
}