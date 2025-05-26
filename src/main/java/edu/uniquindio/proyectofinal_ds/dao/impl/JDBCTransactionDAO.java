package edu.uniquindio.proyectofinal_ds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.dao.TransactionDAO;
import edu.uniquindio.proyectofinal_ds.datastructures.List;
import edu.uniquindio.proyectofinal_ds.model.TransactionRecord;
import edu.uniquindio.proyectofinal_ds.util.DatabaseConnection;

public class JDBCTransactionDAO implements TransactionDAO {

    @Override
    public void saveTransaction(TransactionRecord transaction, Connection conn) throws Exception {
        String sql = "INSERT INTO Transactions (id, type, accountId, amount, timestamp, destinationAccountId) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transaction.getId().toString());
            pstmt.setString(2, transaction.getType());
            pstmt.setString(3, transaction.getAccountId().toString());
            pstmt.setBigDecimal(4, transaction.getAmount());
            pstmt.setString(5, transaction.getTimestamp().toString());
            
            if (transaction.getDestinationAccountId() != null) {
                pstmt.setString(6, transaction.getDestinationAccountId().toString());
            } else {
                pstmt.setNull(6, java.sql.Types.VARCHAR);
            }
            pstmt.executeUpdate();
        }
    }

    @Override
    public void saveTransaction(TransactionRecord transaction) throws Exception {
        String sql = "INSERT INTO Transactions (id, type, accountId, amount, timestamp, destinationAccountId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, transaction.getId().toString());
            pstmt.setString(2, transaction.getType());
            pstmt.setString(3, transaction.getAccountId().toString());
            pstmt.setBigDecimal(4, transaction.getAmount());
            pstmt.setString(5, transaction.getTimestamp().toString());

            if (transaction.getDestinationAccountId() != null) {
                pstmt.setString(6, transaction.getDestinationAccountId().toString());
            } else {
                pstmt.setNull(6, java.sql.Types.VARCHAR);
            }
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<TransactionRecord> getTransactionsByAccountId(UUID accountId) {
        edu.uniquindio.proyectofinal_ds.datastructures.List<TransactionRecord> transactions = new edu.uniquindio.proyectofinal_ds.datastructures.LinkedList<>();
        String sql = "SELECT * FROM Transactions WHERE accountId = ? ORDER BY timestamp DESC";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountId.toString());

            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String type = rs.getString("type");
                    UUID accId = UUID.fromString(rs.getString("accountId"));
                    var amount = rs.getBigDecimal("amount");
                    var timestamp = java.time.LocalDateTime.parse(rs.getString("timestamp"));
                    UUID destinationId = null;
                    String destStr = rs.getString("destinationAccountId");
                    if (destStr != null) {
                        destinationId = UUID.fromString(destStr);
                    }

                    TransactionRecord record = new TransactionRecord(id, type, accId, amount, timestamp, destinationId);
                    transactions.add(record);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener transacciones de la base de datos", e);
        }

        return transactions;
    }

    @Override
    public List<TransactionRecord> getTransactionsByUserId(UUID userId) {
        edu.uniquindio.proyectofinal_ds.datastructures.List<TransactionRecord> transactions = new edu.uniquindio.proyectofinal_ds.datastructures.LinkedList<>();
        String sql = """
            SELECT t.*
            FROM Transactions t
            JOIN Accounts a ON t.accountId = a.id
            WHERE a.userId = ?
            ORDER BY t.timestamp DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId.toString());

            try (var rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String type = rs.getString("type");
                    UUID accountId = UUID.fromString(rs.getString("accountId"));
                    var amount = rs.getBigDecimal("amount");
                    var timestamp = java.time.LocalDateTime.parse(rs.getString("timestamp"));
                    UUID destinationAccountId = null;

                    String destStr = rs.getString("destinationAccountId");
                    if (destStr != null) {
                        destinationAccountId = UUID.fromString(destStr);
                    }

                    TransactionRecord record = new TransactionRecord(id, type, accountId, amount, timestamp, destinationAccountId);
                    transactions.add(record);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener transacciones por usuario", e);
        }

        return transactions;
    }
}