package com.example.msdealer.controller;

import com.example.msdealer.client.OrderClient;
import com.example.msdealer.dto.enumeration.OrderStatus;
import com.example.msdealer.dto.response.OrderResponseDto;
import com.example.msdealer.exception.handler.SuccessDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/order")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {
private final OrderClient orderClient;
    @PutMapping("{id}")
    public ResponseEntity<SuccessDetails<String>> cancelOrder(@PathVariable(name = "id") Long id ) {
      return   orderClient.cancelOrder(id);
    }

    @GetMapping
    public ResponseEntity<SuccessDetails<List<OrderResponseDto>>> getAllOrders(){
        return orderClient.getAllOrders();
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessDetails<OrderResponseDto>> getOrder(@PathVariable(name = "id") Long id )  {
        return orderClient.getOrder(id);
    }

    @PutMapping("dealer/{id}")
    public ResponseEntity<SuccessDetails<String>> updateOrder(@PathVariable(name = "id") Long id, @RequestParam OrderStatus orderStatus)  {
     return    orderClient.updateOrder(id,orderStatus);
    }
}
