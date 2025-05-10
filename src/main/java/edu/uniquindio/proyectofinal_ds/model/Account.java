package edu.uniquindio.proyectofinal_ds.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Account {
    private final UUID id;
    private final UUID userId;
    private AccountType accountType;
    private BigDecimal balance;

    public Account(UUID userId, AccountType accountType) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.accountType = accountType;
        this.balance = BigDecimal.ZERO;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = this.balance.add(amount);
        } else {
            throw new IllegalArgumentException("El monto del depósito debe ser positivo.");
        }
    }

    public boolean withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(balance) <= 0) {
            this.balance = this.balance.subtract(amount);
            return true;
        } else {
            System.out.println("Fondos insuficientes o monto inválido.");
            return false;
        }
    }

    public String getFormattedBalance() {
        return "$" + balance.toPlainString();
    }
}