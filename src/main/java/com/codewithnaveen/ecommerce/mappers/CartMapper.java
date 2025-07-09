package com.codewithnaveen.ecommerce.mappers;

import com.codewithnaveen.ecommerce.dtos.CartDto;
import com.codewithnaveen.ecommerce.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDto toDto(Cart cart);
}
