package com.project.e_commerce.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {

    private int id;
    private String name;
    private BigDecimal price;
    private int stock;
    private String category;
}
