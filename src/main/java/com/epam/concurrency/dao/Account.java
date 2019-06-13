package com.epam.concurrency.dao;

import java.io.Serializable;

public class Account implements Serializable {
    private static final long serialversionUID = 129348938L;

    private Long id;
    private String username;
    private Long balance;

    public Account(Long id, String username, Long balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}
