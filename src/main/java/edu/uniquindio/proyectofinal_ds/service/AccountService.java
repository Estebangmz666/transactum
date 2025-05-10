package edu.uniquindio.proyectofinal_ds.service;

import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.datastructures.HashMap;
import edu.uniquindio.proyectofinal_ds.model.Account;

public class AccountService {

    private static final HashMap<UUID, Account> accountRegistry = new HashMap<>();

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