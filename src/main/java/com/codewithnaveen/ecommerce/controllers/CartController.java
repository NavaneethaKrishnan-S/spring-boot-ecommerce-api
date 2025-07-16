package com.codewithnaveen.ecommerce.controllers;

import com.codewithnaveen.ecommerce.dtos.AddItemToCartRequest;
import com.codewithnaveen.ecommerce.dtos.CartDto;
import com.codewithnaveen.ecommerce.dtos.CartItemDto;
import com.codewithnaveen.ecommerce.dtos.UpdateCartItemRequest;
import com.codewithnaveen.ecommerce.exceptions.CartNotFoundException;
import com.codewithnaveen.ecommerce.exceptions.ProductNotFoundException;
import com.codewithnaveen.ecommerce.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ){

        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addProductToCart(
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest request
            ){

        var cartItemDto = cartService.addItemToCart(cartId, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable UUID cartId){

        return cartService.getCartById(cartId);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateCartItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
            ){

        return cartService.updateItemInCart(cartId, productId, request.getQuantity());
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId
    ){
        cartService.removeItemFromCart(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId){

        cartService.clearAllItemsFromCart(cartId);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("error", "Cart not found")
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", "Product not found in the cart")
        );
    }
}
