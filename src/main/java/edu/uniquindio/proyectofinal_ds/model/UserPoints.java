package edu.uniquindio.proyectofinal_ds.model;

import java.util.UUID;

public class UserPoints {
    public UUID userId;
    public int points;

    public UserPoints(UUID userId, int points) {
        this.userId = userId;
        this.points = points;
    }
}