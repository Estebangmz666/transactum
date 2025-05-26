package edu.uniquindio.proyectofinal_ds.datastructures;

import java.util.UUID;

public class BSTNode {
    UUID key;
    int points;
    BSTNode left, right;

    public BSTNode(UUID key, int points) {
        this.key = key;
        this.points = points;
        this.left = null;
        this.right = null;
    }
}