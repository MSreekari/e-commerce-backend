package com.project.e_commerce.service;

import com.project.e_commerce.dto.order.OrderResponse;
import com.project.e_commerce.entity.CartItem;
import com.project.e_commerce.entity.Order;
import com.project.e_commerce.entity.OrderItem;
import com.project.e_commerce.enums.OrderStatus;
import com.project.e_commerce.repository.CartRepository;
import com.project.e_commerce.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    // place order
    public OrderResponse placeOrder(int userId) {

        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.valueOf("PLACED"));

        // Convert Cart → OrderItems
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem item = new OrderItem();
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice());
            item.setOrder(order);
            return item;
        }).toList();

        order.setOrderItems(orderItems);

        // Calculate total
        BigDecimal total = orderItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        // Clear cart
        cartRepository.deleteAll(cartItems);

        return mapToResponse(savedOrder);
    }

    // ✅ GET ALL ORDERS
    public List<OrderResponse> getOrdersByUser(int userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ✅ GET SINGLE ORDER
    public OrderResponse getOrderById(int userId, int orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {

        // Map OrderItems → DTO OrderItem
        List<com.project.e_commerce.dto.order.OrderItem> items =
                order.getOrderItems().stream().map(i -> {
                    com.project.e_commerce.dto.order.OrderItem dto =
                            new com.project.e_commerce.dto.order.OrderItem();

                    dto.setOrderId(order.getId());
                    dto.setProductName(i.getProduct().getName());
                    dto.setQuantity(i.getQuantity());
                    dto.setPrice(i.getPrice());

                    return dto;
                }).toList();

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setItems(items);

        return response;
    }
}