package com.testalten.backend.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.testalten.backend.dto.CartDTO;
import com.testalten.backend.entity.Cart;
import com.testalten.backend.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        Cart cart = cartService.getCartByUserEmail(email);
        CartDTO cartDTO = new CartDTO(cart.getId(), cart.getItems());
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping
    public ResponseEntity<?> addItem(@RequestParam Long productId, @RequestParam int quantity, Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        Cart cart = cartService.addItemToCart(email, productId, quantity);
        CartDTO cartDTO = new CartDTO(cart.getId(), cart.getItems());
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping
    public ResponseEntity<?> clearCart(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        cartService.clearCart(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message", "Cart cleared"));
    }
}
