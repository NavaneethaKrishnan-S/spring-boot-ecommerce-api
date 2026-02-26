package com.codewithnaveen.ecommerce.controllers;

import com.codewithnaveen.ecommerce.dtos.CheckoutRequest;
import com.codewithnaveen.ecommerce.dtos.CheckoutResponse;
import com.codewithnaveen.ecommerce.dtos.ErrorDto;
import com.codewithnaveen.ecommerce.entities.Order;
import com.codewithnaveen.ecommerce.repositories.CartRepository;
import com.codewithnaveen.ecommerce.repositories.OrderRepository;
import com.codewithnaveen.ecommerce.services.AuthService;
import com.codewithnaveen.ecommerce.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> checkout(
            @Valid @RequestBody CheckoutRequest request
    ){
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null){
            return ResponseEntity.badRequest().body(
                    new ErrorDto("Cart not found")
            );
        }

        if(cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    new ErrorDto("Cart is Empty")
            );
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return ResponseEntity.ok(new CheckoutResponse(order.getId()));
    }
}
