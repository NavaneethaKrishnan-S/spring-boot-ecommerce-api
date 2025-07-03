package com.codewithnaveen.ecommerce.repositories;

import com.codewithnaveen.ecommerce.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}