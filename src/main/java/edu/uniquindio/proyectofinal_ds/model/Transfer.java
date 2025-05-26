package edu.uniquindio.proyectofinal_ds.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.service.AccountService;
import edu.uniquindio.proyectofinal_ds.service.PointsCalculator;
import edu.uniquindio.proyectofinal_ds.service.UserService;
import edu.uniquindio.proyectofinal_ds.util.Session;

public class Transfer extends Transaction {

    private final UUID destinationAccountId;
    private final AccountService accountService = new AccountService();
    private final UserService userService = new UserService();

    public Transfer(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount) {
        super(sourceAccountId, amount);
        this.destinationAccountId = destinationAccountId;
    }

    @Override
    public boolean execute() {
        Account sourceAccount = accountService.getAccountById(accountId);
        Account destinationAccount = accountService.getAccountById(destinationAccountId);

        if (sourceAccount == null || destinationAccount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (!sourceAccount.withdraw(amount)) {
            return false;
        }

        destinationAccount.deposit(amount);

        try {
            if (accountService.updateAccount(sourceAccount) && accountService.updateAccount(destinationAccount)) {
                int pts = PointsCalculator.calculateTransferPoints(amount);
                User user = Session.getCurrentUser();
                user.setPoints(user.getPoints() + pts);
                user.updateRank();
                userService.updateUser(user);
                return true;
            }
            /*boolean updatedSource = accountService.updateAccount(sourceAccount);
            boolean updatedDest = accountService.updateAccount(destinationAccount);
            if (updatedSource && updatedDest) {
                System.out.println("✅ Transferencia exitosa de " + amount + " de " + accountId + " a " + destinationAccountId);
                return true;
            } else {
                System.out.println("Error al actualizar cuentas en la base de datos.");
            }*/
        } catch (Exception e) {
            System.out.println("Error al actualizar cuentas: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public TransactionRecord toRecord() {
        return new TransactionRecord(
            UUID.randomUUID(),
            "TRANSFER",
            accountId,
            amount,
            LocalDateTime.now(),
            destinationAccountId
        );
    }

    public UUID getDestinationAccountId() {
        return destinationAccountId;
    }
}