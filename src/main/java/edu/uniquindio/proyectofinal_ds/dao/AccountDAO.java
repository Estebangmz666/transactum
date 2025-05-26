package edu.uniquindio.proyectofinal_ds.dao;

import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.datastructures.List;
import edu.uniquindio.proyectofinal_ds.model.Account;

public interface AccountDAO {
    Account saveAccount(Account account);
    Account getAccountByID(UUID accountId);
    List<Account> findAccountByUserId(UUID userId) throws Exception;
    List<Account> findAllAccounts() throws Exception;
    boolean updateAccount(Account account) throws Exception;
    boolean deleteAccount(UUID accountId) throws Exception;
}