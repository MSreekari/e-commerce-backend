package com.project.e_commerce.service;

import com.project.e_commerce.dto.product.ProductRequest;
import com.project.e_commerce.dto.product.ProductResponse;
import com.project.e_commerce.entity.Product;
import com.project.e_commerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private Product mapToEntity(ProductRequest req) {
        Product product = new Product();
        product.setProductName(req.getName());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        product.setCategory(req.getCategory());
        return product;
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
    public ProductResponse addProduct(ProductRequest request) {
        Product product = mapToEntity(request);
        Product productResponse = productRepository.save(product);
        return mapToResponse(productResponse);
    }

    // get all products
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToResponse).toList();
    }

    // get a product by id
    public ProductResponse getProductById(int id) {
        Product productResponse = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product with id: " + id + " not found")
        );
        return mapToResponse(productResponse);
    }

    // update a product
    public ProductResponse updateProduct(int id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product with id: " + id + " not found")
        );
        existingProduct.setProductName(productRequest.getName());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStock(productRequest.getStock());
        existingProduct.setCategory(productRequest.getCategory());
        Product updatedProduct = productRepository.save(existingProduct);
        return mapToResponse(updatedProduct);
    }

    // delete a product
    public void deleteProduct(int id) {
        if(!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product with id: " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    // find product by category
    public List<ProductResponse> findProductByCategory(String category) {
        List<Product> products = productRepository.findByCategoryContainingIgnoreCase(category);
        return products.stream().map(this::mapToResponse).toList();
    }

    // find product by name
    public List<ProductResponse> findProductByName(String name) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(name);
        return products.stream().map(this::mapToResponse).toList();
    }

    // search product
    public List<ProductResponse> searchProduct(String keyword) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword);
        return products.stream().map(this::mapToResponse).toList();
    }

}
