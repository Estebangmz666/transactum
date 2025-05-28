package edu.uniquindio.proyectofinal_ds.datastructures;


public class AVLNode<T extends Comparable<T>> {
    public T value;
    public AVLNode<T> left, right;
    public int height;

    public AVLNode(T value) {
        this.value = value;
        this.height = 1;
    }
}