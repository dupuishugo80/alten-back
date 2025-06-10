package com.testalten.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.testalten.backend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCode(String code);
    boolean existsByInternalReference(String internalReference);
}
