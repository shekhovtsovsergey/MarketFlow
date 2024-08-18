package ru.shekhovtsov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shekhovtsov.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
