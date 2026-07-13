package com.project.e_commerce.repository;


import com.project.e_commerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCart_User_Id(int userId);

    Optional<CartItem> findByIdAndCart_User_Id(int cartItemId, int userId);

    Optional<CartItem> findByUser_Id(int userId);
}
