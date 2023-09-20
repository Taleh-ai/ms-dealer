package com.example.msdealer.service.impl;


import com.example.msdealer.client.OrderClient;
import com.example.msdealer.dto.enumeration.OrderStatus;
import com.example.msdealer.dto.response.OrderResponseDto;
import com.example.msdealer.exception.handler.SuccessDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;



import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final OrderClient orderClient;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public void cancelOrder(Long id) {
        logger.info("Cancelling order with ID: {}", id);
        orderClient.cancelOrder(id);
        logger.info("Order cancelled successfully for ID: {}", id);
    }

    public ResponseEntity<SuccessDetails<OrderResponseDto>> getOrderById(Long id) {
        logger.info("Getting order by ID: {}", id);
        ResponseEntity<SuccessDetails<OrderResponseDto>> response = orderClient.getOrder(id);
        logger.info("Received response for order ID {}: {}", id, response.getStatusCode());
        return response;
    }

    public ResponseEntity<SuccessDetails<List<OrderResponseDto>>> getAllOrders() {
        logger.info("Getting all orders");
        ResponseEntity<SuccessDetails<List<OrderResponseDto>>> response = orderClient.getAllOrders();
        logger.info("Received response for all orders: {}", response.getStatusCode());
        return response;
    }

    public void updateOrder(Long id, OrderStatus orderStatus) {
        logger.info("Updating order with ID: {} to status: {}", id, orderStatus);
        orderClient.updateOrder(id, orderStatus);
        logger.info("Order with ID {} updated successfully to status: {}", id, orderStatus);
    }
}
