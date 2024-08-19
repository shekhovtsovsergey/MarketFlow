package ru.shekhovtsov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shekhovtsov.dto.PaymentRequestDto;
import ru.shekhovtsov.dto.PaymentResponseDto;
import ru.shekhovtsov.service.PaymentService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/makePayment")
    public PaymentResponseDto makePayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        return paymentService.makePayment(paymentRequestDto);
    }

}