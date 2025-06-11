package com.testalten.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.testalten.backend.dto.CartDTO;
import com.testalten.backend.dto.CartItemDTO;
import com.testalten.backend.entity.*;
import com.testalten.backend.repository.*;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Cart getCartByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        return cartRepository.findByUser(user)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(user);
                return cartRepository.save(newCart);
            });
    }

    public Cart addItemToCart(String email, Long productId, int quantity) {
        Cart cart = getCartByUserEmail(email);
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        return cartRepository.save(cart);
    }

    public void clearCart(String email) {
        Cart cart = getCartByUserEmail(email);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
