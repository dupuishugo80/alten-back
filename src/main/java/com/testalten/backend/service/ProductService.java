package com.testalten.backend.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.testalten.backend.dto.ProductRequestDTO;
import com.testalten.backend.entity.Product;
import com.testalten.backend.repository.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(ProductRequestDTO productRequest) {
        if (productRepository.existsByCode(productRequest.getCode())) {
            throw new IllegalArgumentException("Product with code already exists");
        }

        if (productRepository.existsByInternalReference(productRequest.getInternalReference())) {
            throw new IllegalArgumentException("Product with internal reference already exists");
        }
        
        Product product = new Product();
        product.setCode(productRequest.getCode());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setImage(productRequest.getImage());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setQuantity(productRequest.getQuantity());
        product.setInternalReference(productRequest.getInternalReference());
        product.setShellId(productRequest.getShellId());
        product.setInventoryStatus(productRequest.getInventoryStatus());
        product.setRating(productRequest.getRating());
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());

        productRepository.save(product);
        return product;
    }
}
