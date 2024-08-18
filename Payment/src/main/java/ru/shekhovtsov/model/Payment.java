package ru.shekhovtsov.model;

public class Payment {
    private Long id;
    private String debitAccount;
    private String creditAccount;
    private Double amount;
    private PaymentStatus status;
}
