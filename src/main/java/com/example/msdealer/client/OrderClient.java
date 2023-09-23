package com.example.msdealer.client;


import com.example.msdealer.dto.enumeration.OrderStatus;
import com.example.msdealer.dto.response.OrderResponseDto;
import com.example.msdealer.exception.handler.SuccessDetails;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mscustomer", url = "http://localhost:8080/v1/orders-feign")
public interface OrderClient {

    @PutMapping("/{id}")
    ResponseEntity<SuccessDetails<String>> cancelOrder(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<SuccessDetails<List<OrderResponseDto>>> getAllOrders();

    @GetMapping("/{id}")
    ResponseEntity<SuccessDetails<OrderResponseDto>> getOrder(@PathVariable("id") Long id);

    @PutMapping("/update/{id}")
    ResponseEntity<SuccessDetails<String>> updateOrder(
            @PathVariable("id") Long id,
            @RequestParam("orderStatus") OrderStatus orderStatus
    );
}