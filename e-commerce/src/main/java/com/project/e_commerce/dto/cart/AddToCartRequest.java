package com.project.e_commerce.dto.cart;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {

    private int productId;

    @Min(1)
    private int quantity;
}
