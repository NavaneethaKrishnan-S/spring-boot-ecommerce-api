package com.codewithnaveen.ecommerce.repositories;

import com.codewithnaveen.ecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
