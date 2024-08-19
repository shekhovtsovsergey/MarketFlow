package ru.shekhovtsov.service;


import ru.shekhovtsov.dto.PaymentRequestDto;
import ru.shekhovtsov.dto.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto);
}
