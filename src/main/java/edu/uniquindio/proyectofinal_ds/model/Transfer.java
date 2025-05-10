package edu.uniquindio.proyectofinal_ds.model;

import java.math.BigDecimal;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.service.AccountService;

public class Transfer extends Transaction {
    private final UUID destinationAccountId;

    public Transfer(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount) {
        super(sourceAccountId, amount);
        this.destinationAccountId = destinationAccountId;
    }

    @Override
    public boolean execute() {
        Account sourceAccount = AccountService.getAccountById(accountId);
        Account destinationAccount = AccountService.getAccountById(destinationAccountId);

        if (sourceAccount != null && destinationAccount != null && sourceAccount.withdraw(amount)) {
            destinationAccount.deposit(amount);
            System.out.println("Transferencia de " + amount + " de " + accountId + " a " + destinationAccountId);
            return true;
        }
        return false;
    }
}