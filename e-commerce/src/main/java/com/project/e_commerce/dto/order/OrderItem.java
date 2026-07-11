package com.project.e_commerce.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItem {
    private int orderId;
    private int productName;
    private int quantity;
    private BigDecimal price;
}
