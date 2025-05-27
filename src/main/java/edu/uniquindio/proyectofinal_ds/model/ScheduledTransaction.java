package edu.uniquindio.proyectofinal_ds.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ScheduledTransaction extends Transaction{
    protected final LocalDateTime scheduledTime;

    public ScheduledTransaction(UUID accountId, BigDecimal amount, LocalDateTime scheduledTime) {
        super(accountId, amount);
        this.scheduledTime = scheduledTime;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public boolean isReadyToExecute() {
        return !LocalDateTime.now().isBefore(scheduledTime);
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public TransactionRecord toRecord() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toRecord'");
    }
}