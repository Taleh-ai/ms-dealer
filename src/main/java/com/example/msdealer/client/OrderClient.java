package com.example.msdealer.client;


import com.example.msdealer.dto.enumeration.OrderStatus;
import com.example.msdealer.dto.response.OrderResponseDto;
import com.example.msdealer.exception.handler.SuccessDetails;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", url = "http://localhost:8080")
public interface OrderClient {

    @PutMapping("/v1/orders/{id}")
    ResponseEntity<SuccessDetails<String>> cancelOrder(@PathVariable("id") Long id);

    @GetMapping("/v1/orders")
    ResponseEntity<SuccessDetails<List<OrderResponseDto>>> getAllOrders();

    @GetMapping("/v1/orders/{id}")
    ResponseEntity<SuccessDetails<OrderResponseDto>> getOrder(@PathVariable("id") Long id);

    @PutMapping("/v1/orders/dealer/{id}")
    ResponseEntity<SuccessDetails<String>> updateOrder(
            @PathVariable("id") Long id,
            @RequestParam("orderStatus") OrderStatus orderStatus
    );
}