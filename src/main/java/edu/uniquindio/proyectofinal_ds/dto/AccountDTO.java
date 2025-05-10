package edu.uniquindio.proyectofinal_ds.dto;

import edu.uniquindio.proyectofinal_ds.model.AccountType;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private UUID userId;
    private AccountType accountType;
}