package com.project.e_commerce.repository;

import com.project.e_commerce.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByCategory(String category);

    Product findByProductName(String productName);

    List<Product> findByCategoryContainsIgnoreCase(String category);

    List<Product> findByCategoryContainingIgnoreCase(String category, Sort sort);

    List<Product> findByCategoryContainingIgnoreCase(String category);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> findByProductNameContainingIgnoreCaseOrCategory(String productName, String category);

    List<Product> findByProductNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String productName, String category);
}
