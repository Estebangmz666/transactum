package edu.uniquindio.proyectofinal_ds.datastructures;

public interface Map<K, V> {
    void put(K key, V value);
    V get(K key);
    boolean remove(K key);
    boolean containsKey(K key);
    int size();
    boolean isEmpty();
    void clear();
}