package edu.uniquindio.proyectofinal_ds.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.dao.PointsDAO;
import edu.uniquindio.proyectofinal_ds.datastructures.LinkedList;
import edu.uniquindio.proyectofinal_ds.datastructures.List;
import edu.uniquindio.proyectofinal_ds.model.UserPoints;
import edu.uniquindio.proyectofinal_ds.util.DatabaseConnection;

public class JDBCPointsDAO implements PointsDAO{

    @Override
    public void update(UUID userId, int points) {
        String sql = "INSERT INTO userPoints (userId, points) VALUES (?, ?) ON CONFLICT(userId) DO UPDATE SET points = excluded.points";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, userId.toString());
                pstmt.setInt(2, points);
                pstmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error al actualizar puntos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Integer get(UUID userId) {
        String sql = "SELECT points FROM userPoints WHERE userId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, userId.toString());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("points");
                }
            } catch (SQLException e){
                System.out.println("Error al obtener puntos: " + e.getMessage());
                e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserPoints> getAll() {
        List<UserPoints> pointsList = new LinkedList<>();
        try (Connection connection = DatabaseConnection.getConnection();
            Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM userPoints");
            while (rs.next()) {
                UUID userId = UUID.fromString(rs.getString("userId"));
                int points = rs.getInt("points");
                pointsList.add(new UserPoints(userId, points));
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar puntos: " + e.getMessage());
            e.printStackTrace();
        }
        return pointsList;
    }
}
