package edu.uniquindio.proyectofinal_ds.datastructures;

import java.util.Iterator;

public class LinkedList<T> implements List<T>, Iterable<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private int size;

    @Override
    public void add(T element) {
        add(size, element);
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();

        Node<T> newNode = new Node<>(element);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> prev = getNode(index - 1);
            newNode.next = prev.next;
            prev.next = newNode;
        }

        size++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).data;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        Node<T> removed;

        if (index == 0) {
            removed = head;
            head = head.next;
        } else {
            Node<T> prev = getNode(index - 1);
            removed = prev.next;
            prev.next = removed.next;
        }

        size--;
        return removed.data;
    }

    @Override
    public boolean remove(T element) {
        if (head == null) return false;

        if (head.data.equals(element)) {
            head = head.next;
            size--;
            return true;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(element)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    @Override
    public int indexOf(T element) {
        Node<T> current = head;
        int index = 0;

        while (current != null) {
            if (current.data.equals(element)) return index;
            current = current.next;
            index++;
        }

        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = head;

        for (int i = 0; i < index; i++) current = current.next;
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }
            @Override
            public T next() {
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }
}