package edu.uniquindio.proyectofinal_ds.datastructures;

import edu.uniquindio.proyectofinal_ds.model.ScheduledTransaction;

public class PriorityQueueScheduledTransactions {

    private static class Node {
        ScheduledTransaction transaction;
        Node next;

        Node(ScheduledTransaction transaction) {
            this.transaction = transaction;
        }
    }

    private Node head;

    public void add(ScheduledTransaction transaction) {
        Node newNode = new Node(transaction);
        if (head == null || head.transaction.getScheduledTime().isAfter(transaction.getScheduledTime())) {
            newNode.next = head;
            head = newNode;
            return;
        }

        Node current = head;
        while (current.next != null && !current.next.transaction.getScheduledTime().isAfter(transaction.getScheduledTime())) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
    }

    public ScheduledTransaction peek() {
        if (head == null) return null;
        return head.transaction;
    }

    public ScheduledTransaction poll() {
        if (head == null) return null;
        ScheduledTransaction tx = head.transaction;
        head = head.next;
        return tx;
    }

    public boolean isEmpty() {
        return head == null;
    }
}