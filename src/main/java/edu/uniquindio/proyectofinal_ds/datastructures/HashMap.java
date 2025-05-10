package edu.uniquindio.proyectofinal_ds.datastructures;

public class HashMap<K, V> implements Map<K,V>{  
    private static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry<K, V>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMap() {
        buckets = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    @Override
    public void put(K key, V value) {
        int index = getIndex(key);
        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;

        if ((double) size / buckets.length > LOAD_FACTOR) {
            resize();
        }
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) return current.value;
            current = current.next;
        }

        return null;
    }

    @Override
    public boolean remove(K key) {
        int index = getIndex(key);
        Entry<K, V> current = buckets[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }

            prev = current;
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldBuckets = buckets;
        buckets = new Entry[oldBuckets.length * 2];
        size = 0;

        for (Entry<K, V> head : oldBuckets) {
            while (head != null) {
                put(head.key, head.value);
                head = head.next;
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        buckets = new Entry[INITIAL_CAPACITY];
        size = 0;
    }
}