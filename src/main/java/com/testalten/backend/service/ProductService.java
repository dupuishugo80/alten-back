package com.testalten.backend.service;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.testalten.backend.dto.ProductPatchDTO;
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

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
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

    public Product patchProduct(Long id, ProductPatchDTO patch) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        if (patch.getCode() != null && !patch.getCode().equals(product.getCode())) {
            if (productRepository.existsByCode(patch.getCode())) {
                throw new IllegalArgumentException("Product with code already exists");
            }
            product.setCode(patch.getCode());
        }

        if (patch.getInternalReference() != null && !patch.getInternalReference().equals(product.getInternalReference())) {
            if (productRepository.existsByInternalReference(patch.getInternalReference())) {
                throw new IllegalArgumentException("Product with internal reference already exists");
            }
            product.setInternalReference(patch.getInternalReference());
        }

        if (patch.getName() != null) product.setName(patch.getName());
        if (patch.getDescription() != null) product.setDescription(patch.getDescription());
        if (patch.getImage() != null) product.setImage(patch.getImage());
        if (patch.getCategory() != null) product.setCategory(patch.getCategory());
        if (patch.getPrice() != null) product.setPrice(patch.getPrice());
        if (patch.getQuantity() != null) product.setQuantity(patch.getQuantity());
        if (patch.getShellId() != null) product.setShellId(patch.getShellId());
        if (patch.getInventoryStatus() != null) product.setInventoryStatus(patch.getInventoryStatus());
        if (patch.getRating() != null) product.setRating(patch.getRating());

        product.setUpdatedAt(Instant.now());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
