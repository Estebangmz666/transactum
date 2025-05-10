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
}