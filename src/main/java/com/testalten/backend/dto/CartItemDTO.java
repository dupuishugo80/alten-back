package com.testalten.backend.dto;

import com.testalten.backend.entity.Product;

public class CartItemDTO {
    private Long id;
    private ProductDTO product;
    private int quantity;

    public CartItemDTO(Long id, Product product, Integer quantity) {
        this.id = id;
        this.product = new ProductDTO(product.getId(), 
            product.getName(), 
            product.getPrice(), 
            product.getDescription(), 
            product.getCode(), 
            product.getImage(), 
            product.getCategory()
        );
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ProductDTO getProduct() {
        return product;
    }
    public void setProduct(ProductDTO product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
