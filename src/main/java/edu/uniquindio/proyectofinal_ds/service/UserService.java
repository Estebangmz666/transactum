package edu.uniquindio.proyectofinal_ds.service;

import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.User;
import edu.uniquindio.proyectofinal_ds.dao.AccountDAO;
import edu.uniquindio.proyectofinal_ds.dao.UserDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCAccountDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCUserDAO;
import edu.uniquindio.proyectofinal_ds.datastructures.HashMap;
import edu.uniquindio.proyectofinal_ds.datastructures.List;
import edu.uniquindio.proyectofinal_ds.dto.UserDTO;
import edu.uniquindio.proyectofinal_ds.mapper.UserMapper;

import java.util.UUID;

public class UserService {

    private static final HashMap<UUID, User> users = new HashMap<>();
    private final UserDAO userDAO;
    private final AccountDAO accountDAO = new JDBCAccountDAO();

    public UserService() {
        this.userDAO = new JDBCUserDAO();
    }

    public User login(String email, String password) throws Exception {
        User user = userDAO.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            loadUserAccounts(user);
            return user;
        }
        return null;
    }

    public void loadUserAccounts(User user) throws Exception {
        List<Account> cuentas = accountDAO.findAccountByUserId(user.getId());

        user.getAccounts().clear();

        for (Account cuenta : cuentas) {
            user.addAccount(cuenta);
            AccountService.registerAccount(cuenta);
        }
    }

    public User registerUser(UserDTO dto) {
        ValidationService.validateEmail(dto.getEmail());
        ValidationService.validateAddress(dto.getAddress());
        ValidationService.validateCellphone(dto.getCellphone());
        ValidationService.validatePassword(dto.getPassword());

        if (userDAO.getUserByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso.");
        }

        User user = UserMapper.INSTANCE.toUser(dto);
        userDAO.saveUser(user);
        
        return userDAO.getUserByEmail(dto.getEmail());
    }

    public User getUserWithAccountsByEmail(String email) throws Exception {
        User user = userDAO.getUserByEmail(email);
        if (user != null) {
            loadUserAccounts(user);
        }
        return user;
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

    public void updateUser(User user) {
        userDAO.updateUser(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        }
    }
}