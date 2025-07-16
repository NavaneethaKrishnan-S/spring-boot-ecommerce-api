package com.codewithnaveen.ecommerce.services;

import com.codewithnaveen.ecommerce.dtos.CartDto;
import com.codewithnaveen.ecommerce.dtos.CartItemDto;
import com.codewithnaveen.ecommerce.entities.Cart;
import com.codewithnaveen.ecommerce.exceptions.CartNotFoundException;
import com.codewithnaveen.ecommerce.exceptions.ProductNotFoundException;
import com.codewithnaveen.ecommerce.mappers.CartMapper;
import com.codewithnaveen.ecommerce.repositories.CartRepository;
import com.codewithnaveen.ecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;
    private ProductRepository productRepository;

    private CartMapper cartMapper;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    public CartItemDto addItemToCart(UUID cartId, Long productId){

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        if(product == null){
            throw new ProductNotFoundException();
        }

        var cartItem = cart.addItem(product);

        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartDto getCartById(UUID cartId){

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        return cartMapper.toDto(cart);
    }

    public CartItemDto updateItemInCart(UUID cartId,
                                      Long productId,
                                      Integer quantity){

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        var cartItem = cart.getItem(productId);

        if(cartItem == null){
            throw new ProductNotFoundException();
        }

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public void removeItemFromCart(UUID cartId, Long productId){

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearAllItemsFromCart(UUID cartId){

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        cart.clear();
        cartRepository.save(cart);
    }
}