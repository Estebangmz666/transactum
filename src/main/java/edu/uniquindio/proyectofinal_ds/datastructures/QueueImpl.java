package edu.uniquindio.proyectofinal_ds.datastructures;

public class QueueImpl<T> implements Queue<T> {

    private final LinkedList<T> list;

    public QueueImpl() {
        this.list = new LinkedList<>();
    }

    @Override
    public void enqueue(T element) {
        list.add(element);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        return list.remove(0); 
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new IllegalStateException("Queue is empty");
        return list.get(0);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
    }
}