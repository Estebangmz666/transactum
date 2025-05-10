package edu.uniquindio.proyectofinal_ds.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public abstract class Transaction {
    protected final UUID id;
    protected final UUID accountId;
    protected final BigDecimal amount;
    protected final LocalDateTime timestamp;

    public Transaction(UUID accountId, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public abstract boolean execute();
}