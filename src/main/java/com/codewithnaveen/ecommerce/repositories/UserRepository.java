package com.codewithnaveen.ecommerce.repositories;

import com.codewithnaveen.ecommerce.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
