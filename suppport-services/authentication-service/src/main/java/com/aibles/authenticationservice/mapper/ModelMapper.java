package com.aibles.authenticationservice.mapper;

import com.aibles.authenticationservice.entity.User;
import dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    @Mappings(
            @Mapping(target = "uid", source = "uid")
    )
    UserDTO convertUserToDTO(User user);
}
