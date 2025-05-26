package edu.uniquindio.proyectofinal_ds.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.service.AccountService;
import edu.uniquindio.proyectofinal_ds.service.PointsCalculator;
import edu.uniquindio.proyectofinal_ds.service.PointsService;
import edu.uniquindio.proyectofinal_ds.service.UserService;
import edu.uniquindio.proyectofinal_ds.util.Session;

public class Withdraw extends Transaction {

    private final AccountService accountService = new AccountService();
    private final UserService userService = new UserService();
    private final PointsService pointsService = new PointsService();

    public Withdraw(UUID accountId, BigDecimal amount) {
        super(accountId, amount);
    }

    @Override
    public boolean execute() {
        Account account = accountService.getAccountById(accountId);
        if (account == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (!account.withdraw(amount)) {
            return false;
        }

        try {
            if (accountService.updateAccount(account)){
                int pts = PointsCalculator.calculateWithdrawPoints(amount);
                UUID userId = Session.getCurrentUser().getId();
                User user = Session.getCurrentUser();
                pointsService.addPoints(userId, pts);
                user.setPoints(user.getPoints() + pts);
                user.updateRank();
                userService.updateUser(user);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar la cuenta: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public TransactionRecord toRecord() {
        return new TransactionRecord(
            UUID.randomUUID(),
            "WITHDRAW",
            accountId,
            amount,
            LocalDateTime.now(),
            null
        );
    }
}