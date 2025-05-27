package edu.uniquindio.proyectofinal_ds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.dao.UserDAO;
import edu.uniquindio.proyectofinal_ds.model.User;
import edu.uniquindio.proyectofinal_ds.model.UserRank;
import edu.uniquindio.proyectofinal_ds.util.DatabaseConnection;

public class JDBCUserDAO implements UserDAO{

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String fullName = rs.getString("fullName");
                    String emailDb = rs.getString("email");
                    String address = rs.getString("address");
                    String cellphone = rs.getString("cellphone");
                    int points = rs.getInt("points");
                    UserRank rank = UserRank.valueOf(rs.getString("rank").toUpperCase());
                    String password = rs.getString("password");

                    return new User(id, fullName, emailDb, password, address, cellphone, points, rank);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO Users (id, fullName, email, password, address, cellphone, points, rank) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getId().toString());
            pstmt.setString(2, user.getFullName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getAddress());
            pstmt.setString(6, user.getCellphone());
            pstmt.setInt(7, user.getPoints());
            pstmt.setString(8, user.getRank().name());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE Users SET fullName = ?, email = ?, password = ?, address = ?, cellphone = ?, points = ?, rank = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getCellphone());
            pstmt.setInt(6, user.getPoints());
            pstmt.setString(7, user.getRank().name());
            pstmt.setString(8, user.getId().toString());

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("No user found with id: " + user.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(String email) {
        String sql = "DELETE FROM Users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            int rowsDeleted = pstmt.executeUpdate();
            
            if (rowsDeleted == 0) {
                System.out.println("No user found with email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean userExists(String email) {
        return getUserByEmail(email) != null;
    }

    @Override
    public boolean validateUser(String email, String password) {
        String sql = "SELECT password FROM Users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return storedPassword.equals(password);
                } else {
                    return false;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserById(UUID id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UUID userId = UUID.fromString(rs.getString("id"));
                    String fullName = rs.getString("fullName");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    String cellphone = rs.getString("cellphone");
                    int points = rs.getInt("points");
                    UserRank rank = UserRank.valueOf(rs.getString("rank").toUpperCase());
                    String password = rs.getString("password");

                    return new User(userId, fullName, email, password, address, cellphone, points, rank);
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}