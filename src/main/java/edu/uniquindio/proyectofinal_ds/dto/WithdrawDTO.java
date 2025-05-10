package edu.uniquindio.proyectofinal_ds.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawDTO {
    private UUID accountId;
    private BigDecimal amount;
}