package com.codewithnaveen.ecommerce.mappers;

import com.codewithnaveen.ecommerce.dtos.OrderDto;
import com.codewithnaveen.ecommerce.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto (Order order);
}