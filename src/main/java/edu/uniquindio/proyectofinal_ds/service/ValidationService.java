package edu.uniquindio.proyectofinal_ds.service;

import java.math.BigDecimal;

public class ValidationService {

    public static void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }
        if (!email.matches("^[\\w.-]+@([\\w-]+\\.)+[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Formato de correo inválido.");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
            throw new IllegalArgumentException(
                "La contraseña debe incluir mayúsculas, minúsculas y dígitos.");
        }
    }

    public static void validateAddress(String address) {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía.");
        }
        if (!address.matches(".*\\d+.*") || address.length() < 5) {
            throw new IllegalArgumentException(
                "La dirección debe tener al menos 5 caracteres e incluir un número.");
        }
    }

    public static void validateCellphone(String cellphone) {
        if (cellphone == null || cellphone.isEmpty()) {
            throw new IllegalArgumentException("El celular no puede estar vacío.");
        }
        if (!cellphone.matches("^3\\d{9}$")) {
            throw new IllegalArgumentException("Formato de celular inválido. Ej: 3001234567");
        }
    }

    public static void validateAccountType(String accountType) {
        if (accountType == null || accountType.isEmpty()) {
            throw new IllegalArgumentException("El tipo de cuenta no puede estar vacío.");
        }
        if (!accountType.equals("Ahorros") && !accountType.equals("Corriente")) {
            throw new IllegalArgumentException("Tipo de cuenta inválido. Debe ser 'Ahorros' o 'Corriente'.");
        }
    }

    public static BigDecimal parseAndValidateAmount(String input) {
        try {
            BigDecimal amount = new BigDecimal(input.trim());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("El monto debe ser mayor a cero.");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingrese un número válido.");
        }
    }
}