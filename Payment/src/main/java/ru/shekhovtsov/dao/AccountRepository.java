package ru.shekhovtsov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shekhovtsov.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(String number);
}
