package edu.uniquindio.proyectofinal_ds.datastructures;

public interface Stack<T> {
    void push(T element);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
    void clear();
}