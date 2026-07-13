package com.project.e_commerce.controller;

import com.project.e_commerce.dto.cart.AddToCartRequest;
import com.project.e_commerce.dto.cart.CartItemResponse;
import com.project.e_commerce.dto.cart.CartResponse;
import com.project.e_commerce.entity.Cart;
import com.project.e_commerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CartItemResponse> addCartItem(@PathVariable int userId, @RequestBody AddToCartRequest cartItemRequest){
        CartItemResponse cartItemResponse = cartService.addToCart(userId, cartItemRequest);
        return ResponseEntity.ok(cartItemResponse);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<CartItemResponse> updateCartItem(@RequestParam int userId, @PathVariable int cartId, @RequestParam int quantity){
        CartItemResponse cartItemResponse = cartService.updateCart(userId, cartId, quantity);
        return ResponseEntity.ok(cartItemResponse);
    }

    @GetMapping
    public ResponseEntity<CartResponse> getAllCartItems(@RequestParam int userId){
        CartResponse cartResponse = cartService.getAllItemsInCart(userId);
        return ResponseEntity.ok(cartResponse);
    }

    @GetMapping("/item/{cartId}")
    public ResponseEntity<CartItemResponse> getCartItemById(@RequestParam int userId, @PathVariable int cartId){
        CartItemResponse cartItemResponse = cartService.getCartItemById(userId, cartId);
        return ResponseEntity.ok(cartItemResponse);
    }

    @DeleteMapping("/item/{cartId}")
    public ResponseEntity<Void> deleteCartItemById(@PathVariable int cartId){
        cartService.deleteCartItem(cartId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@RequestParam int userId){
        cartService.deleteCartByUserId(userId);
        return ResponseEntity.ok().build();
    }
}
