package edu.uniquindio.proyectofinal_ds.datastructures;

public class StackImpl<T> implements Stack<T> {

    private final LinkedList<T> list;

    public StackImpl() {
        this.list = new LinkedList<>();
    }

    @Override
    public void push(T element) {
        list.add(0, element);
    }

    @Override
    public T pop() {
        if (isEmpty()) throw new IllegalStateException("Stack is empty");
        return list.remove(0);
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new IllegalStateException("Stack is empty");
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