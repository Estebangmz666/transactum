package edu.uniquindio.proyectofinal_ds.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private UUID id;
    private UUID accountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String type;
}