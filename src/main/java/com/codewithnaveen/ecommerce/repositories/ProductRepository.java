package com.codewithnaveen.ecommerce.repositories;

import com.codewithnaveen.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}