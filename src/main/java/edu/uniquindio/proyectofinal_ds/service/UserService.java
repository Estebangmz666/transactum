package edu.uniquindio.proyectofinal_ds.service;

import edu.uniquindio.proyectofinal_ds.model.User;
import edu.uniquindio.proyectofinal_ds.dao.UserDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCUserDAO;
import edu.uniquindio.proyectofinal_ds.datastructures.HashMap;
import edu.uniquindio.proyectofinal_ds.dto.UserDTO;
import edu.uniquindio.proyectofinal_ds.mapper.UserMapper;

import java.util.UUID;

public class UserService {

    private static final HashMap<UUID, User> users = new HashMap<>();
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new JDBCUserDAO();
    }

    public User registerUser(UserDTO dto) {
        AuthService.validateEmail(dto.getEmail());
        AuthService.validateAddress(dto.getAddress());
        AuthService.validateCellphone(dto.getCellphone());
        AuthService.validatePassword(dto.getPassword());

        if (userDAO.getUserByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso.");
        }

        User user = UserMapper.INSTANCE.toUser(dto);
        userDAO.saveUser(user);
        
        return userDAO.getUserByEmail(dto.getEmail());
    }

    public static void addUser(User user) {
        users.put(user.getId(), user);
    }

    public static User getUserById(UUID userId) {
        return users.get(userId);
    }

    public static User getUserByAccountId(UUID accountId) {
        for (User user : users.values()) {
            if (user.getAccount(accountId) != null) {
                return user;
            }
        }
        return null;
    }

    public static HashMap<UUID, User> getAllUsers() {
        return users;
    }
}