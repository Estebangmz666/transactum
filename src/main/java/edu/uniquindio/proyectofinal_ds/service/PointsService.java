package edu.uniquindio.proyectofinal_ds.service;

import java.math.BigDecimal;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.dao.PointsDAO;
import edu.uniquindio.proyectofinal_ds.dao.UserDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCPointsDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCUserDAO;
import edu.uniquindio.proyectofinal_ds.datastructures.List;
import edu.uniquindio.proyectofinal_ds.datastructures.PointsBST;
import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.User;
import edu.uniquindio.proyectofinal_ds.model.UserPoints;

public class PointsService {
    private final PointsBST pointsTree = new PointsBST();
    private final PointsDAO pointsDAO = new JDBCPointsDAO();
    private final UserDAO userDAO = new JDBCUserDAO();

    public void addPoints(UUID userId, int pointsToAdd) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            System.out.println("Usuario no encontrado: " + userId);
            return;
        }
        int updatedPoints = user.getPoints() + pointsToAdd;
        user.setPoints(updatedPoints);
        userDAO.updateUser(user);
        pointsTree.insertOrUpdate(userId, pointsToAdd);
        pointsDAO.update(userId, pointsToAdd);
    }

    public int getPoints(UUID userId) {
        User user = userDAO.getUserById(userId);
        if (user == null) return 0;
        return user.getPoints();
    }

    public boolean redeemPoints(UUID userId, UUID accountId, int pointsToRedeem) throws Exception {
        if (pointsToRedeem < 100) return false;

        User user = userDAO.getUserById(userId);
        if (user == null) return false;

        int currentPoints = user.getPoints();
        if (currentPoints < pointsToRedeem) return false;

        BigDecimal bonus = BigDecimal.valueOf(pointsToRedeem)
            .divide(BigDecimal.valueOf(100))
            .multiply(BigDecimal.valueOf(5000));

        AccountService accountService = new AccountService();
        Account account = accountService.getAccountById(accountId);
        if (account == null) return false;

        account.deposit(bonus);
        accountService.updateAccount(account);

        user.setPoints(currentPoints - pointsToRedeem);
        userDAO.updateUser(user);

        return true;
    }

    public void loadFromDatabase() {
        List<UserPoints> all = pointsDAO.getAll();
        for (UserPoints up : all) {
            pointsTree.insertOrUpdate(up.userId, up.points);
        }
    }
}