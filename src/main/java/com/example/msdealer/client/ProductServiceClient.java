package com.example.msdealer.client;


import com.example.msdealer.dto.response.OrderResponseDto;
import com.example.msdealer.exception.ResourceNotFoundException;
import com.example.msdealer.exception.handler.SuccessDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mscustomer", url = "http://localhost:8080")
public interface ProductServiceClient {

    @PutMapping("{id}")
    public ResponseEntity<SuccessDetails<String>> cancelOrder(@PathVariable(name = "id") Long id ) throws ResourceNotFoundException;
    
    @GetMapping
    public ResponseEntity<SuccessDetails<List<OrderResponseDto>>> getAllOrders();
    @GetMapping("{id}")
    public ResponseEntity<SuccessDetails<OrderResponseDto>> getOrder(@PathVariable(name = "id") Long id ) throws ResourceNotFoundException ;
}