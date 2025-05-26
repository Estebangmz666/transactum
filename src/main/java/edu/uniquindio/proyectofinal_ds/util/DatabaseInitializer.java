package edu.uniquindio.proyectofinal_ds.util;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseInitializer {
    private static final String DB_URL = PropertiesLoader.getPathFromProperties("DB_PATH");

    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS Users (
                id TEXT PRIMARY KEY,
                fullName TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                address TEXT NOT NULL,
                cellphone TEXT NOT NULL,
                points INTEGER NOT NULL DEFAULT 0,
                rank TEXT DEFAULT 'BRONZE' CHECK (rank IN ('BRONZE', 'SILVER', 'GOLD', 'PLATINUM'))
            );
            """;

            String createAccountsTable = """
                CREATE TABLE IF NOT EXISTS Accounts (
                id TEXT PRIMARY KEY,
                userId TEXT NOT NULL,
                accountType TEXT NOT NULL CHECK (accountType IN ('SAVINGS', 'CHECKING', 'CREDIT')),
                balance NUMERIC NOT NULL CHECK (balance >= 0),
                FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE CASCADE
            );
            """;

            String createTransactionsTable = """
                CREATE TABLE IF NOT EXISTS Transactions (
                id TEXT PRIMARY KEY,
                type TEXT NOT NULL CHECK (type IN ('DEPOSIT', 'WITHDRAW', 'TRANSFER')),
                accountId TEXT NOT NULL,
                amount REAL NOT NULL CHECK (amount > 0),
                timestamp TEXT NOT NULL,
                destinationAccountId TEXT, -- solo se usa si es TRANSFER
                FOREIGN KEY (accountId) REFERENCES Accounts(id),
                FOREIGN KEY (destinationAccountId) REFERENCES Accounts(id)
            );
            """;

            String createUserPointsTable = """
                    CREATE TABLE IF NOT EXISTS userPoints (
                    userId TEXT PRIMARY KEY,
                    points INTEGER NOT NULL       
            );
            """;

            statement.execute(createUsersTable);
            System.out.println("Tabla 'Users' creada o ya existe.");

            statement.execute(createAccountsTable);
            System.out.println("Tabla 'Accounts' creada o ya existe.");

            statement.execute(createTransactionsTable);
            System.out.println("Tabla 'Transactions' creada o ya existe.");

            statement.execute(createUserPointsTable);
            System.out.println("Tabla 'userPoints' creada o ya existe.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}