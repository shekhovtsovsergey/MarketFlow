package ru.shekhovtsov.dao;

import ru.shekhovtsov.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountDao {
    Optional<Account> findById(Long id);
    Optional<Account> findByNumber(String number);
    void save(Account account);
    void updateBalance(Long id, BigDecimal newBalance);
}
