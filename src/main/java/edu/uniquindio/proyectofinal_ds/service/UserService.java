package edu.uniquindio.proyectofinal_ds.service;

import edu.uniquindio.proyectofinal_ds.model.User;
import edu.uniquindio.proyectofinal_ds.datastructures.HashMap;
import java.util.UUID;

public class UserService {

    private static final HashMap<UUID, User> users = new HashMap<>();

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