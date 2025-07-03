package com.codewithnaveen.ecommerce.repositories;

import com.codewithnaveen.ecommerce.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}