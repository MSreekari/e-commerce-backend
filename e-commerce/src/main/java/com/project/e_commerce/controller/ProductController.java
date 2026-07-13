package com.project.e_commerce.controller;

import com.project.e_commerce.dto.product.ProductRequest;
import com.project.e_commerce.dto.product.ProductResponse;
import com.project.e_commerce.entity.Product;
import com.project.e_commerce.repository.CartItemRepository;
import com.project.e_commerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService  productService;
    private final CartItemRepository cartItemRepository;

    public ProductController(ProductService productService, CartItemRepository cartItemRepository) {
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductRequest product) {
        ProductResponse newProduct = productService.addProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable int id, @RequestBody ProductRequest product) {
        ProductResponse updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> findByCategory(@PathVariable String category) {
        List<ProductResponse> productResponse = productService.findProductByCategory(category);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/name")
    public ResponseEntity<List<ProductResponse>> findByProductName(@RequestParam String name) {
        List<ProductResponse> productResponse = productService.findProductByName(name);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String name, @RequestParam String category) {
        List<ProductResponse> productResponse = productService.searchProduct(name, category);
        return ResponseEntity.ok(productResponse);
    }
}
