package edu.uniquindio.proyectofinal_ds.util;

import edu.uniquindio.proyectofinal_ds.model.User;
import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.AccountType;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class TestDataService {
    public static List<User> getBasicUserSet() {
        List<User> users = new ArrayList<>();

        User user1 = new User("Esteban Gómez León", "esteban.gomezl@uqvirtual.edu.co", "Universidad del Quindío", "3053273449");
        Account acc11 = new Account(user1.getId(), AccountType.CHECKING);
        Account acc12 = new Account(user1.getId(), AccountType.SAVINGS);
        user1.addAccount(acc11);
        user1.addAccount(acc12);
        acc11.setBalance(new BigDecimal("100000"));
        acc12.setBalance(new BigDecimal("50000"));
        user1.addPoints(5562);

        User user2 = new User("Robinson Arias", "rarias@uqvirtual.edu.co", "Universidad del Quindío", "666");
        Account acc21 = new Account(user2.getId(), AccountType.CHECKING);
        Account acc22 = new Account(user2.getId(), AccountType.SAVINGS);
        user2.addAccount(acc21);
        user2.addAccount(acc22);
        acc21.setBalance(new BigDecimal("40200"));
        acc22.setBalance(new BigDecimal("1350000"));
        user2.addPoints(1500);

        User user3 = new User("María López", "mlopez@uqvirtual.edu.co", "Calle 50 #23-45", "3111234567");
        Account acc31 = new Account(user3.getId(), AccountType.CHECKING);
        Account acc32 = new Account(user3.getId(), AccountType.SAVINGS);
        user3.addAccount(acc31);
        user3.addAccount(acc32);
        acc31.setBalance(new BigDecimal("40200"));
        acc32.setBalance(new BigDecimal("1350000"));
        user3.addPoints(900);

        User user4 = new User("Carlos Gómez", "cgomez@uqvirtual.edu.co", "Avenida Siempre Viva 742", "3127654321");
        Account acc41 = new Account(user4.getId(), AccountType.CHECKING);
        Account acc42 = new Account(user4.getId(), AccountType.SAVINGS);
        user4.addAccount(acc41);
        user4.addAccount(acc42);
        acc41.setBalance(new BigDecimal("125000"));
        acc42.setBalance(new BigDecimal("850000"));
        user4.addPoints(1200);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        return users;
    }

    public static List<User> getHugeAccountsSet() {
        List<User> users = new ArrayList<>();

        User user1 = new User("Alejandro Vargas", "alejandro.vargas@uqvirtual.edu.co", "Carrera 10 #20-30", "3001234567");
        Account acc11 = new Account(user1.getId(), AccountType.CHECKING);
        Account acc12 = new Account(user1.getId(), AccountType.SAVINGS);
        user1.addAccount(acc11);
        user1.addAccount(acc12);
        acc11.setBalance(new BigDecimal("50000000"));
        acc12.setBalance(new BigDecimal("120000000"));
        user1.addPoints(9000);
        users.add(user1);

        User user2 = new User("María Fernanda Ruiz", "mruiz@uqvirtual.edu.co", "Av. Siempre Viva 742", "3109876543");
        Account acc21 = new Account(user2.getId(), AccountType.CHECKING);
        Account acc22 = new Account(user2.getId(), AccountType.SAVINGS);
        user2.addAccount(acc21);
        user2.addAccount(acc22);
        acc21.setBalance(new BigDecimal("75000000"));
        acc22.setBalance(new BigDecimal("90000000"));
        user2.addPoints(11000);
        users.add(user2);

        User user3 = new User("José Martínez", "jmartinez@uqvirtual.edu.co", "Calle 5 #10-20", "3151237890");
        Account acc31 = new Account(user3.getId(), AccountType.CHECKING);
        Account acc32 = new Account(user3.getId(), AccountType.SAVINGS);
        user3.addAccount(acc31);
        user3.addAccount(acc32);
        acc31.setBalance(new BigDecimal("100000000"));
        acc32.setBalance(new BigDecimal("150000000"));
        user3.addPoints(15000);
        users.add(user3);

        User user4 = new User("Luisa Gómez", "lgomez@uqvirtual.edu.co", "Carrera 15 #40-50", "3149876543");
        Account acc41 = new Account(user4.getId(), AccountType.CHECKING);
        Account acc42 = new Account(user4.getId(), AccountType.SAVINGS);
        user4.addAccount(acc41);
        user4.addAccount(acc42);
        acc41.setBalance(new BigDecimal("80000000"));
        acc42.setBalance(new BigDecimal("110000000"));
        user4.addPoints(13000);
        users.add(user4);

        return users;
    }

    public static List<User> getUsersWithPointsSet() {
        List<User> users = new ArrayList<>();

        User user1 = new User("Laura Méndez", "lmendez@uqvirtual.edu.co", "Calle 12 #34-56", "3101234567");
        Account acc1 = new Account(user1.getId(), AccountType.CHECKING);
        user1.addAccount(acc1);
        acc1.setBalance(new BigDecimal("50000"));
        user1.addPoints(250);

        User user2 = new User("Andrés Pérez", "aperez@uqvirtual.edu.co", "Carrera 9 #23-45", "3119876543");
        Account acc2 = new Account(user2.getId(), AccountType.SAVINGS);
        user2.addAccount(acc2);
        acc2.setBalance(new BigDecimal("120000"));
        user2.addPoints(800);

        User user3 = new User("Catalina Ruiz", "cruiz@uqvirtual.edu.co", "Av. Central 100", "3121237890");
        Account acc3 = new Account(user3.getId(), AccountType.CHECKING);
        user3.addAccount(acc3);
        acc3.setBalance(new BigDecimal("200000"));
        user3.addPoints(2000);

        User user4 = new User("Jorge Herrera", "jherrera@uqvirtual.edu.co", "Calle 7 #8-9", "3139876543");
        Account acc4 = new Account(user4.getId(), AccountType.SAVINGS);
        user4.addAccount(acc4);
        acc4.setBalance(new BigDecimal("400000"));
        user4.addPoints(5500);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        return users;
    }

    public static List<User> getCrossTransactionSet() {
        List<User> users = new ArrayList<>();

        User user1 = new User("Sofía Ramírez", "sramirez@uqvirtual.edu.co", "Calle 15 #10-20", "3104567890");
        Account user1_checking = new Account(user1.getId(), AccountType.CHECKING);
        Account user1_savings = new Account(user1.getId(), AccountType.SAVINGS);
        user1.addAccount(user1_checking);
        user1.addAccount(user1_savings);
        user1_checking.setBalance(new BigDecimal("150000"));
        user1_savings.setBalance(new BigDecimal("300000"));
        user1.addPoints(1000);

        User user2 = new User("Pedro Castillo", "pcastillo@uqvirtual.edu.co", "Av. Libertad 50", "3115678901");
        Account user2_checking = new Account(user2.getId(), AccountType.CHECKING);
        Account user2_savings = new Account(user2.getId(), AccountType.SAVINGS);
        user2.addAccount(user2_checking);
        user2.addAccount(user2_savings);
        user2_checking.setBalance(new BigDecimal("100000"));
        user2_savings.setBalance(new BigDecimal("200000"));
        user2.addPoints(800);

        User user3 = new User("Ana Torres", "atorres@uqvirtual.edu.co", "Calle 3 #4-5", "3126789012");
        Account user3_checking = new Account(user3.getId(), AccountType.CHECKING);
        Account user3_savings = new Account(user3.getId(), AccountType.SAVINGS);
        user3.addAccount(user3_checking);
        user3.addAccount(user3_savings);
        user3_checking.setBalance(new BigDecimal("50000"));
        user3_savings.setBalance(new BigDecimal("100000"));
        user3.addPoints(600);

        User user4 = new User("Luis Fernández", "lfernandez@uqvirtual.edu.co", "Carrera 20 #30-40", "3137890123");
        Account user4_checking = new Account(user4.getId(), AccountType.CHECKING);
        Account user4_savings = new Account(user4.getId(), AccountType.SAVINGS);
        user4.addAccount(user4_checking);
        user4.addAccount(user4_savings);
        user4_checking.setBalance(new BigDecimal("75000"));
        user4_savings.setBalance(new BigDecimal("125000"));
        user4.addPoints(1200);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        // Ejemplo (pseudocódigo):
        // userA.transferTo(userB, amount);
        // userB.transferTo(userC, amount);
        // etc.

        return users;
    }
}