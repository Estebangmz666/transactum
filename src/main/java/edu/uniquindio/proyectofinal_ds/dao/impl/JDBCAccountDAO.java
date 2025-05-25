package edu.uniquindio.proyectofinal_ds.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.dao.AccountDAO;
import edu.uniquindio.proyectofinal_ds.datastructures.List;
import edu.uniquindio.proyectofinal_ds.datastructures.LinkedList;
import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.AccountType;
import edu.uniquindio.proyectofinal_ds.util.DatabaseConnection;

public class JDBCAccountDAO implements AccountDAO {

    @Override
    public Account saveAccount(Account account){
        String sql = "INSERT INTO Accounts (id, userId, accountType, balance) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getId().toString());
            pstmt.setString(2, account.getUserId().toString());
            pstmt.setString(3, account.getAccountType().name());
            pstmt.setBigDecimal(4, account.getBalance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la cuenta en la base de datos", e);
        }
        return account;
    }

    @Override
    public Account getAccountByID(UUID accountId) {
        String sql = "SELECT * FROM Accounts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountId.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAccount(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener cuenta de la base de datos", e);
        }
        return null;
    }

    @Override
    public List<Account> findAccountByUserId(UUID userId) throws Exception {
        String sql = "SELECT * FROM Accounts WHERE userId = ?";
        edu.uniquindio.proyectofinal_ds.datastructures.List<Account> accounts = new LinkedList<>();

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapRowToAccount(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la cuenta en la base de datos", e);
        }
        return accounts;
    }


    @Override
    public boolean updateAccount(Account account) throws Exception {
        String sql = "UPDATE Accounts SET accountType = ?, balance = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getAccountType().name());
            pstmt.setBigDecimal(2, account.getBalance());
            pstmt.setString(3, account.getId().toString());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la cuenta en la base de datos", e);
        }
    }

    @Override
    public boolean deleteAccount(UUID accountId) throws Exception {
        String sql = "DELETE FROM Accounts WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountId.toString());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la cuenta en la base de datos", e);
        }
    }

    private Account mapRowToAccount(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        UUID userId = UUID.fromString(rs.getString("userId"));
        AccountType type = AccountType.valueOf(rs.getString("accountType"));
        BigDecimal balance = rs.getBigDecimal("balance");

        return new Account(id, userId, type, balance);
    }
}