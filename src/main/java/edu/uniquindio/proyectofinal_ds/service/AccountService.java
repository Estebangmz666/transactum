package edu.uniquindio.proyectofinal_ds.service;

import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.dao.AccountDAO;
import edu.uniquindio.proyectofinal_ds.dao.impl.JDBCAccountDAO;
import edu.uniquindio.proyectofinal_ds.datastructures.HashMap;
import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.AccountType;

public class AccountService {

    private final AccountDAO accountDAO;

    private static final HashMap<UUID, Account> accountRegistry = new HashMap<>();

    public AccountService() {
        this.accountDAO = new JDBCAccountDAO();
    }

    public Account createAccount(UUID userId, String accountTypeDescription) {
        if (accountTypeDescription == null || accountTypeDescription.isBlank()) {
            throw new IllegalArgumentException("Debe seleccionar un tipo de cuenta.");
        }

        AccountType accountType = AccountType.getAccountTypeFromDescription(accountTypeDescription);
        if (accountType == null) {
            throw new IllegalArgumentException("Tipo de cuenta inválido.");
        }

        Account account = new Account(userId, accountType);
        return accountDAO.saveAccount(account);
    }

    public boolean deleteAccount(Account account) throws Exception {
        if (account == null) {
            return false;
        }
        return accountDAO.deleteAccount(account.getId());
    }

    public static Account getAccountById(UUID accountId) {
        return accountRegistry.get(accountId);
    }

    public static void registerAccount(Account account) {
        accountRegistry.put(account.getId(), account);
    }

    public static void removeAccount(UUID accountId) {
        accountRegistry.remove(accountId);
    }

    public static void clearAccounts() {
        accountRegistry.clear();
    }
}