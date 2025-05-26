package edu.uniquindio.proyectofinal_ds.service;

import java.math.BigDecimal;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.dao.PointsDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCPointsDAO;
import edu.uniquindio.proyectofinal_ds.datastructures.List;
import edu.uniquindio.proyectofinal_ds.datastructures.PointsBST;
import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.UserPoints;

public class PointsService {
    private final PointsBST pointsTree = new PointsBST();
    private final PointsDAO pointsDAO = new JDBCPointsDAO();

    public void addPoints(UUID userId, int points) {
        pointsTree.insertOrUpdate(userId, points);
        pointsDAO.update(userId, points);
    }

    public int getPoints(UUID userId) {
        Integer result = pointsTree.search(userId);
        return result != null ? result : 0;
    }

    public boolean redeemPoints(UUID userId, UUID accountId, int pointsToRedeem) throws Exception {
        int currentPoints = pointsTree.search(userId);
        if (currentPoints < pointsToRedeem || pointsToRedeem < 100) {
            return false;
        }

        BigDecimal bonus = BigDecimal.valueOf(pointsToRedeem / 100 * 5000);
        AccountService accountService = new AccountService();
        Account account = accountService.getAccountById(accountId);

        if (account == null) return false;

        account.deposit(bonus);
        accountService.updateAccount(account);

        int updatedPoints = currentPoints - pointsToRedeem;
        pointsTree.insertOrUpdate(userId, updatedPoints);
        pointsDAO.update(userId, updatedPoints);

        return true;
    }

    public void loadFromDatabase() {
        List<UserPoints> all = pointsDAO.getAll();
        for (UserPoints up : all) {
            pointsTree.insertOrUpdate(up.userId, up.points);
        }
    }
}