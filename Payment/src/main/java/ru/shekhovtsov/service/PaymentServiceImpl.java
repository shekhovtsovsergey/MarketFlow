package ru.shekhovtsov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shekhovtsov.dao.AccountRepository;
import ru.shekhovtsov.dao.PaymentRepository;
import ru.shekhovtsov.dto.PaymentRequestDto;
import ru.shekhovtsov.dto.PaymentResponseDto;
import ru.shekhovtsov.dto.ProductRequestDto;
import ru.shekhovtsov.dto.ProductResponseDto;
import ru.shekhovtsov.model.Account;
import ru.shekhovtsov.model.Payment;
import ru.shekhovtsov.model.PaymentStatus;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentDao;
    private final AccountRepository accountDao;
    private final RestTemplate restTemplate;
    private final LimitService limitService;

    @Value("${product-service-url}")
    private String productServiceUrl;

    private static final String CREDIT_ACCOUNT = "30301";

    @Override
    public PaymentResponseDto makePayment(PaymentRequestDto paymentRequestDto) {
        Optional<Account> clientAccount = accountDao.findById(paymentRequestDto.getClientId());
        if (clientAccount.isEmpty()) {
            return new PaymentResponseDto(false, "Клиент не найден.");
        }

        String url = productServiceUrl + "/" + paymentRequestDto.getProductId();
        BigDecimal price = restTemplate.getForObject(url, BigDecimal.class);
        BigDecimal amount = price.multiply(BigDecimal.valueOf(paymentRequestDto.getQuantity()));

        Payment payment = new Payment();
        payment.setDebitAccount(clientAccount.get().getNumber());
        payment.setAmount(amount);

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


        ProductResponseDto productResponse = restTemplate.postForObject(productServiceUrl + "/reserve", productRequestDto, ProductResponseDto.class);


        if (productResponse != null && productResponse.isReserved()) {
            savedPayment.setStatus(PaymentStatus.COMPLETED);
            paymentDao.save(savedPayment);
            limitService.reduceLimit(paymentRequestDto.getClientId(), savedPayment.getAmount());

            return new PaymentResponseDto(true, "Платеж прошел успешно, продукты зарезервированы.");
        } else {
            cancelPayment(savedPayment);
            return new PaymentResponseDto(false, "Продукты не зарезервированы: " + (productResponse != null ? productResponse.getMessage() : "Неизвестная ошибка."));
        }
    }


    @Transactional
    public Payment reservePayment(Payment payment) {
        Optional<Account> account = accountDao.findByNumber(payment.getDebitAccount());
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Счет debitAccount не найден в таблице.");
        }

        BigDecimal amount = payment.getAmount();
        if (account.get().getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Остаток по счету debitAccount недостаточен.");
        }

        BigDecimal newBalance = account.get().getBalance().subtract(amount);
        account.get().setBalance(newBalance);
        accountDao.save(account.get());

        Payment paymentNew = new Payment(null, payment.getDebitAccount(), CREDIT_ACCOUNT, payment.getAmount(), PaymentStatus.PENDING);
        paymentDao.save(paymentNew);

        return paymentNew;
    }


    private void cancelPayment(Payment payment) {
        Optional<Account> debitAccount = accountDao.findByNumber(payment.getDebitAccount());
        if (debitAccount.isPresent()) {
            Account account = debitAccount.get();
            BigDecimal amount = payment.getAmount();
            BigDecimal newBalance = account.getBalance().add(amount);
            account.setBalance(newBalance);
            accountDao.save(account);
            limitService.reduceLimit(Long.valueOf(debitAccount.get().getClient().getId()), payment.getAmount());
        }
        payment.setStatus(PaymentStatus.CANCELLED);
        paymentDao.save(payment);
    }
}

