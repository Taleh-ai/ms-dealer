package com.example.msdealer.controller;

import com.example.msdealer.dto.response.ProductResponseDto;
import com.example.msdealer.exception.ResourceNotFoundException;
import com.example.msdealer.exception.handler.SuccessDetails;
import com.example.msdealer.service.ProductForCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/product-feign")
public class ProductForCustomerController {
    private final ProductForCustomerService productForCustomerService;

    @GetMapping
    public ResponseEntity<SuccessDetails<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> products = productForCustomerService.getAllProducts();
        return  ResponseEntity.ok(new SuccessDetails<>(products, HttpStatus.OK.value(),true));
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessDetails<ProductResponseDto>> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
        ProductResponseDto product = productForCustomerService.getProductById(id);
        return  ResponseEntity.ok(new SuccessDetails<>(product, HttpStatus.OK.value(),true));
    }
}
