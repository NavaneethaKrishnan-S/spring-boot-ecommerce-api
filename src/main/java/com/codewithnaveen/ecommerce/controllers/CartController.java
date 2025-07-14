package com.codewithnaveen.ecommerce.controllers;

import com.codewithnaveen.ecommerce.dtos.AddItemToCartRequest;
import com.codewithnaveen.ecommerce.dtos.CartDto;
import com.codewithnaveen.ecommerce.dtos.CartItemDto;
import com.codewithnaveen.ecommerce.entities.Cart;
import com.codewithnaveen.ecommerce.entities.CartItem;
import com.codewithnaveen.ecommerce.mappers.CartMapper;
import com.codewithnaveen.ecommerce.repositories.CartRepository;
import com.codewithnaveen.ecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ){
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto = cartMapper.toDto(cart);
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addProductToCart(
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest request
            ){

        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if(product == null){
            return ResponseEntity.badRequest().build();
        }

        var cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }

        cartRepository.save(cart);

        var cartItemDto = cartMapper.toDto(cartItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }
}
