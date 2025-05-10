package edu.uniquindio.proyectofinal_ds.service;

import edu.uniquindio.proyectofinal_ds.datastructures.LinkedList;
import edu.uniquindio.proyectofinal_ds.model.*;

import java.util.UUID;

public class TransactionService {

    private static final LinkedList<Transaction> transactions = new LinkedList<>();

    public static boolean executeTransaction(Transaction transaction) {
        boolean result = transaction.execute();
        if (result) {
            transactions.add(transaction);
        }
        return result;
    }

    public static LinkedList<Transaction> getAllTransactions() {
        return transactions;
    }

    public static LinkedList<Transaction> getTransactionsByAccount(UUID accountId) {
        LinkedList<Transaction> result = new LinkedList<>();
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (transaction.getAccountId().equals(accountId)) {
                result.add(transaction);
            } else if (transaction instanceof Transfer transfer) {
                if (transfer.getDestinationAccountId().equals(accountId)) {
                    result.add(transfer);
                }
            }
        }
        return result;
    }

    public static void clearHistory() {
        transactions.clear();
    }

    public static void registerTransaction(Transaction transaction) {
        boolean success = transaction.execute();

        if (success) {
            Account account = AccountService.getAccountById(transaction.getAccountId());
            User user = UserService.getUserByAccountId(account.getId());

            if (user != null) {
                int points = calculatePoints(transaction);
                user.addPoints(points);
                System.out.println("Granted " + points + " points to user " + user.getFullName());
            }
        }
    }

    private static int calculatePoints(Transaction transaction) {
        int base = transaction.getAmount().intValue();

        if (transaction instanceof Deposit) {
            return base * 2;
        } else if (transaction instanceof Transfer) {
            return base;
        } else if (transaction instanceof Withdraw) {
            return base / 2;
        }
        return 0;
    }
}