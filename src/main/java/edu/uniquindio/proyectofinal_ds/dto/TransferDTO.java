package edu.uniquindio.proyectofinal_ds.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
    private UUID sourceAccountId;
    private UUID destinationAccountId;
    private BigDecimal amount;
}