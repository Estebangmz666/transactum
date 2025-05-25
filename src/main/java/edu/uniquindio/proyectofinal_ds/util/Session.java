package edu.uniquindio.proyectofinal_ds.util;

import edu.uniquindio.proyectofinal_ds.model.User;

public class Session {

    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void clearSession() {
        currentUser = null;
    }
}