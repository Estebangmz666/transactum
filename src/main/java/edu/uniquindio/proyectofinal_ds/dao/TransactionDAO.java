package edu.uniquindio.proyectofinal_ds.dao;

import java.sql.Connection;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.datastructures.List;
import edu.uniquindio.proyectofinal_ds.model.TransactionRecord;

public interface TransactionDAO {
    void saveTransaction(TransactionRecord transaction) throws Exception;
    void saveTransaction(TransactionRecord transaction, Connection conn) throws Exception;
    List<TransactionRecord> getTransactionsByAccountId(UUID accountId) throws Exception;
    List<TransactionRecord> getTransactionsByUserId(UUID userId) throws Exception;
}