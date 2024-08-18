package ru.shekhovtsov.dao;

import ru.shekhovtsov.model.Payment;
import ru.shekhovtsov.model.PaymentStatus;

import java.util.Optional;

public interface PaymentDao {
    Optional<Payment> findById(Long id);
    void save(Payment payment);
    void updateStatus(Long id, PaymentStatus status);
}
