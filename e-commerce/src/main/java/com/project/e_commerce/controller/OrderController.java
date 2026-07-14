package com.project.e_commerce.controller;

import com.project.e_commerce.dto.order.OrderResponse;
import com.project.e_commerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestParam int userId) {
        return ResponseEntity.ok(orderService.placeOrder(userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable int userId) {
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @RequestParam int userId,
            @PathVariable int orderId) {

        return ResponseEntity.ok(orderService.getOrderById(userId, orderId));
    }
}
