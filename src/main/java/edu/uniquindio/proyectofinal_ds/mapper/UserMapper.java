package edu.uniquindio.proyectofinal_ds.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import edu.uniquindio.proyectofinal_ds.dto.UserDTO;
import edu.uniquindio.proyectofinal_ds.model.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserDTO dto);
    UserDTO toDTO(User user);
}