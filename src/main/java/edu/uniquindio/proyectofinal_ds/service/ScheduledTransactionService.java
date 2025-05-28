package edu.uniquindio.proyectofinal_ds.service;

import edu.uniquindio.proyectofinal_ds.datastructures.PriorityQueueScheduledTransactions;
import edu.uniquindio.proyectofinal_ds.model.ScheduledTransaction;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ScheduledTransactionService {

    private static final ScheduledTransactionService instance = new ScheduledTransactionService();
    private final PriorityQueueScheduledTransactions scheduledQueue = new PriorityQueueScheduledTransactions();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1); 
    // hilo que verifica las transacciones pendientes almacenadas en la cola

    private ScheduledTransactionService() {
        // Verificar transacciones pendientes cada minuto
        executorService.scheduleAtFixedRate(this::checkDueTransactions, 
            0, 1, TimeUnit.MINUTES);
    }

    public static ScheduledTransactionService getInstance() {
        return instance;
    }

    public void scheduleTransaction(ScheduledTransaction transaction) {
        scheduledQueue.add(transaction);
        scheduleExecution(transaction);
    }

    private void scheduleExecution(ScheduledTransaction transaction) {
        long delay = ChronoUnit.MILLIS.between(LocalDateTime.now(), transaction.getScheduledTime());
        
        if (delay > 0) {
            executorService.schedule(() -> {
                if (transaction.execute()) {
                    removeTransaction(transaction);
                }
            }, delay, TimeUnit.MILLISECONDS);
        }
    }

    public LinkedList<ScheduledTransaction> executeDueTransactions() {
        LinkedList<ScheduledTransaction> executed = new LinkedList<>();
        
        while (!scheduledQueue.isEmpty()) {
            ScheduledTransaction next = scheduledQueue.peek();
            if (next != null && next.isReadyToExecute()) {
                scheduledQueue.poll();
                if (next.execute()) {
                    executed.add(next);
                }
            } else {
                break;
            }
        }
        return executed;
    }

    private void removeTransaction(ScheduledTransaction transaction) {
        // En una implementación con lista enlazada, necesitaríamos un método remove
        // Como tu PriorityQueue no lo tiene, implementamos una solución alternativa
        PriorityQueueScheduledTransactions newQueue = new PriorityQueueScheduledTransactions();
        ScheduledTransaction current;
        boolean removed = false;
        
        while ((current = scheduledQueue.poll()) != null) {
            if (!current.equals(transaction) || removed) {
                newQueue.add(current);
            } else {
                removed = true;
            }
        }
        
        // Transferir todos los elementos de vuelta a la cola original
        while ((current = newQueue.poll()) != null) {
            scheduledQueue.add(current);
        }
    }

    private void checkDueTransactions() {
        if (!scheduledQueue.isEmpty()) {
            executeDueTransactions();
        }
    }

    public boolean hasScheduledTransactions() {
        return !scheduledQueue.isEmpty();
    }
}