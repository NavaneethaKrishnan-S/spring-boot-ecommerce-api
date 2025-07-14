package com.codewithnaveen.ecommerce.mappers;

import com.codewithnaveen.ecommerce.dtos.CartDto;
import com.codewithnaveen.ecommerce.dtos.CartItemDto;
import com.codewithnaveen.ecommerce.entities.Cart;
import com.codewithnaveen.ecommerce.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
