package edu.uniquindio.proyectofinal_ds.dao;

import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.model.User;

public interface UserDAO {
    User getUserByEmail(String email);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(String email);
    boolean userExists(String email);
    boolean validateUser(String email, String password);
    User getUserById(UUID id);
}