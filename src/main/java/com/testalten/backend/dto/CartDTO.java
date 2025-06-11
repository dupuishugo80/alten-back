package com.testalten.backend.dto;

import java.util.List;

import com.testalten.backend.entity.CartItem;

public class CartDTO {
    private Long id;
    private List<CartItemDTO> items;

    public CartDTO(Long id, List<CartItem> items) {
        this.id = id;
        this.items = items.stream()
            .map(item -> new CartItemDTO(item.getId(), item.getProduct(), item.getQuantity()))
            .toList();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<CartItemDTO> getItems() {
        return items;
    }
    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
    
}
