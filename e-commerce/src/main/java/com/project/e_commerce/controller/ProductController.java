package com.project.e_commerce.controller;

import com.project.e_commerce.dto.product.ProductResponse;
import com.project.e_commerce.entity.Product;
import com.project.e_commerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService  productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody Product product) {
        ProductResponse newProduct = productService.addProduct(product);
        return ResponseEntity.ok(newProduct);
    }
}
