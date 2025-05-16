package edu.uniquindio.proyectofinal_ds.model;

import edu.uniquindio.proyectofinal_ds.datastructures.HashMap;
import java.util.UUID;
import lombok.Getter;

@Getter
public class User {
    private final UUID id;
    private String fullName;
    private String email;
    private String address;
    private String cellphone;
    private final HashMap<UUID, Account> accounts;
    private int points = 0;
    private UserRank rank = UserRank.BRONZE;

    public User(String fullName, String email, String address, String cellphone) {
        this.id = UUID.randomUUID();
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.cellphone = cellphone;
        this.accounts = new HashMap<>();
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Account getAccount(UUID id) {
        return accounts.get(id);
    }

    public void addAccount(Account account) {
        this.accounts.put(account.getId(), account);
    }

    public void removeAccount(UUID id) {
        this.accounts.remove(id);
    }

    public void addPoints(int earnedPoints) {
        this.points += earnedPoints;
        updateRank();
    }

    private void updateRank() {
        if (points >= 5000) {
            rank = UserRank.PLATINUM;
        } else if (points >= 1001) {
            rank = UserRank.GOLD;
        } else if (points >= 501) {
            rank = UserRank.SILVER;
        } else if (points >= 0) {
            rank = UserRank.BRONZE;
        }    
    }

    public UserRank getRank() {
        return rank;
    }

    public String getRankDisplayName() {
        return switch (rank) {
            case PLATINUM -> "Platino";
            case GOLD -> "Oro";
            case SILVER -> "Plata";
            case BRONZE -> "Bronce";
        };
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", points=" + points +
                ", rank=" + rank +
                '}';
    }
}