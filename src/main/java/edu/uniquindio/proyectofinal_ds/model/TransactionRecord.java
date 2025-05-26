package edu.uniquindio.proyectofinal_ds.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRecord {
    private UUID id;
    private String type; // "DEPOSIT", "WITHDRAW", "TRANSFER"
    private UUID accountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private UUID destinationAccountId;
}