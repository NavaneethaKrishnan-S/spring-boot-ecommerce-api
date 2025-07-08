package com.codewithnaveen.ecommerce.mappers;

import com.codewithnaveen.ecommerce.dtos.ProductDto;
import com.codewithnaveen.ecommerce.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);
}
