package edu.uniquindio.proyectofinal_ds.mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import edu.uniquindio.proyectofinal_ds.dto.UserDTO;
import edu.uniquindio.proyectofinal_ds.model.User;
import edu.uniquindio.proyectofinal_ds.model.UserRank;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    default User toUser(UserDTO dto) {
        return new User(
            UUID.randomUUID(),
            dto.getFullName(),
            dto.getEmail(),
            dto.getPassword(),
            dto.getAddress(),
            dto.getCellphone(),
            0,
            UserRank.BRONZE
        );
    }
}