package com.project.e_commerce.repository;

import com.project.e_commerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCart_User_Id(int userId);

    Optional<CartItem> findByIdAndCart_User_Id(int cartItemId, int userId);

    void deleteProductById(int id);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.product.id = :productId")
    void deleteByProductId(@Param("productId") int productId);

}
