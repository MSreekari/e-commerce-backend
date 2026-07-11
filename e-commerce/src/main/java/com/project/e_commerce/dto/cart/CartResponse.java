package com.project.e_commerce.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartResponse {
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
}
