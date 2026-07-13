package com.project.e_commerce.repository;


import com.project.e_commerce.entity.Cart;
import com.project.e_commerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser_Id(int userId);

    Optional<Cart> findByIdAndUser_Id(int cartId, int userId);

}
