package edu.uniquindio.proyectofinal_ds.service;

import java.math.BigDecimal;
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
        System.out.println("Cuenta creada con ID: " + account.getId());
        return accountDAO.saveAccount(account);
    }

    public boolean deleteAccount(Account account) throws Exception {
        if (account == null) {
            return false;
        }
        return accountDAO.deleteAccount(account.getId());
    }

    public Account getAccountById(UUID accountId) {
        return accountDAO.getAccountByID(accountId);
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

    public boolean updateAccount(Account account) throws Exception {
        return accountDAO.updateAccount(account);
    }

    public BigDecimal getAccountBalance(UUID accountId) {
        Account account = accountDAO.getAccountByID(accountId);
        if (account != null) {
            return account.getBalance();
        }
        throw new RuntimeException("Cuenta no encontrada: " + accountId);
    }

    public java.util.List<Account> findAccountsByUserId(UUID userId) {
        try {
            edu.uniquindio.proyectofinal_ds.datastructures.List<Account> customList = accountDAO.findAccountByUserId(userId);
            return edu.uniquindio.proyectofinal_ds.util.ListUtils.toJavaList(customList);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener cuentas del usuario " + userId, e);
        }
    }

    public java.util.List<Account> findAllAccounts() {
        try {
            edu.uniquindio.proyectofinal_ds.datastructures.List<Account> customList = accountDAO.findAllAccounts();
            return edu.uniquindio.proyectofinal_ds.util.ListUtils.toJavaList(customList);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener todas las cuentas", e);
        }
    }
}