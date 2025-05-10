package edu.uniquindio.proyectofinal_ds.model;

import java.math.BigDecimal;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.service.AccountService;

public class Withdraw extends Transaction {
    public Withdraw(UUID accountId, BigDecimal amount) {
        super(accountId, amount);
    }

    @Override
    public boolean execute() {
        Account account = AccountService.getAccountById(accountId);
        if (account != null && account.withdraw(amount)) {
            System.out.println("Retiro exitoso de " + amount + " en cuenta " + accountId);
            return true;
        }
        return false;
    }
}