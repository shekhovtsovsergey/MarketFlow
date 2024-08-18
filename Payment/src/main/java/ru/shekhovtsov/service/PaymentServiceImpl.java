package ru.shekhovtsov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shekhovtsov.dao.AccountDao;
import ru.shekhovtsov.dao.PaymentDao;
import ru.shekhovtsov.dto.PaymentRequestDto;
import ru.shekhovtsov.dto.PaymentResponseDto;
import ru.shekhovtsov.dto.ProductRequestDto;
import ru.shekhovtsov.dto.ProductResponseDto;
import ru.shekhovtsov.model.Account;
import ru.shekhovtsov.model.Payment;
import ru.shekhovtsov.model.PaymentStatus;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDao paymentDao;
    private final AccountDao accountDao;
    private final RestTemplate restTemplate;

    @Value("${product-service-url}")
    private String productServiceUrl;

    private static final String CREDIT_ACCOUNT = "30301";

    @Override
    public PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto) {
        Optional<Account> clientAccount = accountDao.findById(paymentRequestDto.getClientId());
        if (clientAccount.isEmpty()) {
            return new PaymentResponseDto(false, "Клиент не найден.");
        }

        // Создаем новый платеж
        Payment payment = new Payment();
        payment.setDebitAccount(clientAccount.get().getNumber());
        payment.setAmount(100.0);

        Payment savedPayment;

        try {
            savedPayment = reservePayment(payment);
        } catch (IllegalArgumentException e) {
            return new PaymentResponseDto(false, e.getMessage());
        }

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setClientId(paymentRequestDto.getClientId());
        productRequestDto.setProductId(paymentRequestDto.getProductId());
        productRequestDto.setQuantity(paymentRequestDto.getQuantity());


        ProductResponseDto productResponse = restTemplate.postForObject(productServiceUrl, productRequestDto, ProductResponseDto.class);


        if (productResponse != null && productResponse.isReserved()) {
            savedPayment.setStatus(PaymentStatus.COMPLETED);
            paymentDao.save(savedPayment);
            return new PaymentResponseDto(true, "Платеж прошел успешно, продукты зарезервированы.");
        } else {
            cancelPayment(savedPayment);
            return new PaymentResponseDto(false, "Продукты не зарезервированы: " + (productResponse != null ? productResponse.getMessage() : "Неизвестная ошибка."));
        }
    }



    private Payment reservePayment(Payment payment) {
        Optional<Account> debitAccount = accountDao.findByNumber(payment.getDebitAccount());
        if (debitAccount.isEmpty()) {
            throw new IllegalArgumentException("Счет debitAccount не найден в таблице.");
        }

        BigDecimal amount = BigDecimal.valueOf(payment.getAmount());
        if (debitAccount.get().getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Остаток по счету debitAccount недостаточен.");
        }

        BigDecimal newBalance = debitAccount.get().getBalance().subtract(amount);
        debitAccount.get().setBalance(newBalance);
        accountDao.updateBalance(debitAccount.get().getId(), newBalance);

        Payment paymentNew = new Payment(null, payment.getDebitAccount(), CREDIT_ACCOUNT, payment.getAmount(), PaymentStatus.PENDING);
        paymentDao.save(paymentNew); // Сохраняем новый платеж

        return paymentNew;
    }


    private void cancelPayment(Payment payment) {
        Optional<Account> debitAccount = accountDao.findByNumber(payment.getDebitAccount());
        if (debitAccount.isPresent()) {
            BigDecimal amount = BigDecimal.valueOf(payment.getAmount());
            BigDecimal newBalance = debitAccount.get().getBalance().add(amount);
            debitAccount.get().setBalance(newBalance);
            accountDao.updateBalance(debitAccount.get().getId(), newBalance);
        }
        payment.setStatus(PaymentStatus.CANCELLED);
        paymentDao.save(payment);
    }
}

