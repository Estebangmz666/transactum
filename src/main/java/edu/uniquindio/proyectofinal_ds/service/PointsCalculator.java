package edu.uniquindio.proyectofinal_ds.service;

import java.math.BigDecimal;

public class PointsCalculator {
    
    public static int calculateDepositPoints(BigDecimal amount) {
        return amount.divide(BigDecimal.valueOf(100)).intValue();
    }

    public static int calculateWithdrawPoints(BigDecimal amount) {
        return amount.divide(BigDecimal.valueOf(100)).intValue() * 2;
    }

    public static int calculateTransferPoints(BigDecimal amount) {
        return amount.divide(BigDecimal.valueOf(100)).intValue() * 3;
    }
}