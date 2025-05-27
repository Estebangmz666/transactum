package edu.uniquindio.proyectofinal_ds.service;

import edu.uniquindio.proyectofinal_ds.datastructures.PriorityQueueScheduledTransactions;
import edu.uniquindio.proyectofinal_ds.model.ScheduledTransaction;

import java.util.LinkedList;

public class ScheduledTransactionService {

    private final PriorityQueueScheduledTransactions scheduledQueue = new PriorityQueueScheduledTransactions();

    public void scheduleTransaction(ScheduledTransaction transaction) {
        scheduledQueue.add(transaction);
    }

    /**
     * Ejecuta todas las transacciones programadas que ya estén listas.
     * Retorna la lista de transacciones ejecutadas.
     */
    public LinkedList<ScheduledTransaction> executeDueTransactions() {
        LinkedList<ScheduledTransaction> executed = new LinkedList<>();

        while (!scheduledQueue.isEmpty()) {
            ScheduledTransaction next = scheduledQueue.peek();
            if (next.isReadyToExecute()) {
                scheduledQueue.poll();
                if (next.execute()) {
                    executed.add(next);
                    // Opcional: registrar la transacción en el historial normal
                    TransactionService.executeTransaction(next);
                }
            } else {
                break;
            }
        }
        return executed;
    }

    public boolean hasScheduledTransactions() {
        return !scheduledQueue.isEmpty();
    }
}