package com.project.e_commerce.service;

import com.project.e_commerce.dto.product.ProductResponse;
import com.project.e_commerce.entity.Product;
import com.project.e_commerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse res = new ProductResponse();
        res.setId(product.getId());
        res.setName(product.getProductName());
        res.setPrice(product.getPrice());
        res.setStock(product.getStock());
        res.setCategory(product.getCategory());
        return res;
    }

    // add a new product
    public ProductResponse addProduct(Product product) {
        Product productResponse = productRepository.save(product);
        return mapToResponse(productResponse);
    }

    // get a product by id
    public ProductResponse getProductById(int id) {
        Product productResponse = productRepository.findById(id).orElse(null);
        return mapToResponse(productResponse);
    }

}
