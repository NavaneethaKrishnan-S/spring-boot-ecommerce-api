package com.codewithnaveen.ecommerce.controllers;

import com.codewithnaveen.ecommerce.entities.User;
import com.codewithnaveen.ecommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getuser(@PathVariable Long id){
        return userRepository.findById(id).orElse(null);
    }

}
