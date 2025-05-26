package edu.uniquindio.proyectofinal_ds.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.service.AccountService;
import edu.uniquindio.proyectofinal_ds.service.PointsCalculator;
import edu.uniquindio.proyectofinal_ds.service.UserService;
import edu.uniquindio.proyectofinal_ds.util.Session;

public class Deposit extends Transaction {

    private final AccountService accountService = new AccountService();
    private final UserService userService = new UserService();

    public Deposit(UUID accountId, BigDecimal amount) {
        super(accountId, amount);
    }

    @Override
    public boolean execute() {
        Account account = accountService.getAccountById(accountId);
        if (account == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        account.deposit(amount);

        try {
            if (accountService.updateAccount(account)){
                int pts = PointsCalculator.calculateDepositPoints(amount);
                User user = Session.getCurrentUser();
                user.setPoints(user.getPoints() + pts);
                user.updateRank();
                userService.updateUser(user);
                return true;
            }
            /*boolean updated = accountService.updateAccount(account);
            if (updated) {
                System.out.println("✅ Depósito exitoso de " + amount + " en cuenta " + accountId);
                return true;
            } else {
                System.out.println("No se pudo actualizar el saldo en la base de datos.");
            }*/
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
            "DEPOSIT",
            accountId,
            amount,
            LocalDateTime.now(),
            null
        );
    }
}