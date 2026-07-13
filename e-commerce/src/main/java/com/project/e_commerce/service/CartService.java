package com.project.e_commerce.service;

import com.project.e_commerce.dto.cart.AddToCartRequest;
import com.project.e_commerce.dto.cart.CartItemResponse;
import com.project.e_commerce.dto.cart.CartResponse;
import com.project.e_commerce.entity.Cart;
import com.project.e_commerce.entity.CartItem;
import com.project.e_commerce.entity.Product;
import com.project.e_commerce.entity.User;
import com.project.e_commerce.repository.CartItemRepository;
import com.project.e_commerce.repository.CartRepository;
import com.project.e_commerce.repository.ProductRepository;
import com.project.e_commerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository,
                       CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    private CartItem mapToEntity(AddToCartRequest cartRequest, Product product, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartRequest.getQuantity());
        return cartItem;
    }

    private CartItemResponse mapToResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setProductId(cartItem.getProduct().getId());
        response.setProductName(cartItem.getProduct().getProductName());
        response.setPrice(cartItem.getProduct().getPrice());
        response.setQuantity(cartItem.getQuantity());
        response.setTotalPrice(
                cartItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
        );
        return response;
    }

    // add to cart
    public CartItemResponse addToCart(int userId, AddToCartRequest cartRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cart = cartRepository.findByUser_Id(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = mapToEntity(cartRequest, product, cart);
        CartItem saved = cartItemRepository.save(cartItem);
        return mapToResponse(saved);
    }

    // update cart item
    public CartItemResponse updateCart(int userId, int cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository
                .findByIdAndCart_User_Id(cartItemId, userId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(quantity);

        return mapToResponse(cartItemRepository.save(cartItem));
    }

    // get all items in a cart
    public CartResponse getAllItemsInCart(int userId) {

        List<CartItem> items = cartItemRepository.findByCart_User_Id(userId);

        List<CartItemResponse> responses = items.stream()
                .map(this::mapToResponse)
                .toList();

        BigDecimal total = responses.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartResponse res = new CartResponse();
        res.setItems(responses);
        res.setTotalAmount(total);

        return res;
    }

    // get a cart item
    public CartItemResponse getCartItemById(int cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        return mapToResponse(item);
    }

    // delete a cart item
    public void deleteCartItem(int id) {
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(item);
    }

    // delete a cart
    public void deleteCartByUserId(int userId) {
        List<CartItem> items = cartItemRepository.findByCart_User_Id(userId);
        cartItemRepository.deleteAll(items);
    }
}