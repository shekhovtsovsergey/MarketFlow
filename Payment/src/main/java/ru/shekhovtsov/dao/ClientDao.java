package ru.shekhovtsov.dao;

import ru.shekhovtsov.model.Client;

import java.util.Optional;

public interface ClientDao {
    Optional<Client> findById(Long id);
    void save(Client client);
}
