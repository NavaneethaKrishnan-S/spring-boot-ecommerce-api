package com.codewithnaveen.ecommerce.controllers;

import com.codewithnaveen.ecommerce.dtos.AddItemToCartRequest;
import com.codewithnaveen.ecommerce.dtos.CartDto;
import com.codewithnaveen.ecommerce.dtos.CartItemDto;
import com.codewithnaveen.ecommerce.dtos.UpdateCartItemRequest;
import com.codewithnaveen.ecommerce.exceptions.CartNotFoundException;
import com.codewithnaveen.ecommerce.exceptions.ProductNotFoundException;
import com.codewithnaveen.ecommerce.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    @Operation(summary = "Create a new Cart")
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ){

        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Add products to a cart")
    public ResponseEntity<CartItemDto> addProductToCart(
            @Parameter(description = "The ID of the Cart")
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest request
            ){

        var cartItemDto = cartService.addItemToCart(cartId, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    @Operation(summary = "Get cart details by Cart ID")
    public CartDto getCart(@PathVariable UUID cartId){

        return cartService.getCartById(cartId);
    }

    @PutMapping("/{cartId}/items/{productId}")
    @Operation(summary = "Update a product quantity in a cart")
    public CartItemDto updateCartItem(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
            ){

        return cartService.updateItemInCart(cartId, productId, request.getQuantity());
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    @Operation(summary = "Remove a item from a cart")
    public ResponseEntity<?> removeItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId
    ){
        cartService.removeItemFromCart(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    @Operation(summary = "Empty a cart")
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
