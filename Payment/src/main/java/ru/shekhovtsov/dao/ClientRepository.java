package ru.shekhovtsov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shekhovtsov.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
