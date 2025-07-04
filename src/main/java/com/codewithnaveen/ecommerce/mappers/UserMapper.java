package com.codewithnaveen.ecommerce.mappers;

import com.codewithnaveen.ecommerce.dtos.UserDto;
import com.codewithnaveen.ecommerce.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);
}
