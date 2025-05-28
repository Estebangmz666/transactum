package edu.uniquindio.proyectofinal_ds.datastructures;

import java.util.ArrayList;
import java.util.List;

public class AVLTree<T extends Comparable<T>> {
    private AVLNode<T> root;

    public void insert(T value) {
        root = insert(root, value);
    }

    private AVLNode<T> insert(AVLNode<T> node, T value) {
        if (node == null) return new AVLNode<>(value);

        int cmp = value.compareTo(node.value);
        if (cmp < 0) node.left = insert(node.left, value);
        else if (cmp > 0) node.right = insert(node.right, value);
        else node.value = value; // update if same

        updateHeight(node);
        return balance(node);
    }

    public List<T> inOrder() {
        List<T> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(AVLNode<T> node, List<T> result) {
        if (node == null) return;
        inOrder(node.left, result);
        result.add(node.value);
        inOrder(node.right, result);
    }

    private int height(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(AVLNode<T> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int getBalance(AVLNode<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private AVLNode<T> balance(AVLNode<T> node) {
        int balance = getBalance(node);
        if (balance > 1) {
            if (getBalance(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1) {
            if (getBalance(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private AVLNode<T> rotateRight(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        AVLNode<T> T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AVLNode<T> rotateLeft(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }
}