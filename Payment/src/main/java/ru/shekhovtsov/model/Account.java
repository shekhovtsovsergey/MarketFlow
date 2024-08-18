package ru.shekhovtsov.model;

import java.math.BigDecimal;

public class Account {
    private Long id;
    private String number;
    private Client client;
    private BigDecimal balance;
    private String status;

    public Account(Long id, String number, Client client, BigDecimal balance, String status) {
        this.id = id;
        this.number = number;
        this.client = client;
        this.balance = balance;
        this.status = status;
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
