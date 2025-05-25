package edu.uniquindio.proyectofinal_ds.model;

public enum AccountType {
    SAVINGS("Cuenta de Ahorros"),
    CHECKING("Cuenta Corriente"),
    CREDIT("Cuenta de Crédito");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static AccountType getAccountTypeFromDescription(String description) {
        for (AccountType type : AccountType.values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        return null;
    }
}