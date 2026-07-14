package com.project.e_commerce.repository;

import com.project.e_commerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(int userId);

    Optional<Order> findByIdAndUserId(int orderId, int userId);
}