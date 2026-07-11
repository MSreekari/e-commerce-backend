package com.project.e_commerce.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemResponse {
    private int productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private int totalPrice;
}
