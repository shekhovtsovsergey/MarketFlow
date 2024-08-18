package ru.shekhovtsov.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String debitAccount;
    private String creditAccount;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}