package com.codewithnaveen.ecommerce.repositories;

import com.codewithnaveen.ecommerce.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}