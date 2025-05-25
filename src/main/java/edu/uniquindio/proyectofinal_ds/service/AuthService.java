package edu.uniquindio.proyectofinal_ds.service;

public class AuthService {
    
    public static void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
    }

    public static void validateAddress(String address) {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        if (address.length() < 5) {
            throw new IllegalArgumentException("Address must be at least 5 characters long");
        }
    }

    public static void validateCellphone(String cellphone) {
        if (cellphone == null || cellphone.isEmpty()) {
            throw new IllegalArgumentException("Cellphone cannot be empty");
        }
        if (!cellphone.matches("\\d{9}")) {
            throw new IllegalArgumentException("Invalid cellphone format");
        }
    }

    public static void validateAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        if (!accountNumber.matches("\\d{10,16}")) {
            throw new IllegalArgumentException("Invalid account number format");
        }
    }

    public static void validateAccountType(String accountType) {
        if (accountType == null || accountType.isEmpty()) {
            throw new IllegalArgumentException("Account type cannot be empty");
        }
        if (!accountType.equals("Ahorros") && !accountType.equals("Corriente")) {
            throw new IllegalArgumentException("Invalid account type");
        }
    }
}