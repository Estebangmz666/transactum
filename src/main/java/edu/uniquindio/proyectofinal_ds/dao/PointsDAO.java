package edu.uniquindio.proyectofinal_ds.dao;

import java.util.UUID;

import edu.uniquindio.proyectofinal_ds.datastructures.List;
import edu.uniquindio.proyectofinal_ds.model.UserPoints;

public interface PointsDAO {
    void update(UUID userId, int points);
    Integer get(UUID userId);
    List<UserPoints> getAll();
}