package com.codewithnaveen.ecommerce.mappers;

import com.codewithnaveen.ecommerce.dtos.RegisterUserRequest;
import com.codewithnaveen.ecommerce.dtos.UpdateUserRequest;
import com.codewithnaveen.ecommerce.dtos.UserDto;
import com.codewithnaveen.ecommerce.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
