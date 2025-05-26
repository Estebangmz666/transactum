package edu.uniquindio.proyectofinal_ds.datastructures;

import java.util.UUID;

public class PointsBST {
    private BSTNode root;

    public PointsBST() {
        root = null;
    }

    public Integer search(UUID key) {
        BSTNode node = searchRecursive(root, key);
        return node == null ? null : node.points;
    }

    private BSTNode searchRecursive(BSTNode current, UUID key) {
        if (current == null) {
            return null;
        }
        int cmp = key.compareTo(current.key);
        if (cmp == 0) {
            return current;
        } else if (cmp < 0) {
            return searchRecursive(current.left, key);
        } else {
            return searchRecursive(current.right, key);
        }
    }

    public void insertOrUpdate(UUID key, int pointsToAdd) {
        root = insertOrUpdateRecursive(root, key, pointsToAdd);
    }

    private BSTNode insertOrUpdateRecursive(BSTNode current, UUID key, int pointsToAdd) {
        if (current == null) {
            return new BSTNode(key, pointsToAdd);
        }
        int cmp = key.compareTo(current.key);
        if (cmp == 0) {
            current.points += pointsToAdd;
        } else if (cmp < 0) {
            current.left = insertOrUpdateRecursive(current.left, key, pointsToAdd);
        } else {
            current.right = insertOrUpdateRecursive(current.right, key, pointsToAdd);
        }
        return current;
    }

    public void inorderTraversal() {
        inorderRecursive(root);
    }

    private void inorderRecursive(BSTNode node) {
        if (node != null) {
            inorderRecursive(node.left);
            System.out.println("Cliente: " + node.key + ", Puntos: " + node.points);
            inorderRecursive(node.right);
        }
    }
}