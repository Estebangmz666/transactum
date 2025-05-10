package edu.uniquindio.proyectofinal_ds.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String address;
    private String cellphone;
}