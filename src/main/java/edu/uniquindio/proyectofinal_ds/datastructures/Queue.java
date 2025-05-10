package edu.uniquindio.proyectofinal_ds.datastructures;

public interface Queue<T> {
    void enqueue(T element);
    T dequeue();
    T peek();
    boolean isEmpty();
    int size();
    void clear();
}