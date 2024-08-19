package ru.shekhovtsov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shekhovtsov.model.Limit;

import java.util.Optional;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
    Optional<Limit> findByClientId(Long clientId);
}