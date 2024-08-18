package ru.shekhovtsov.dao;


import ru.shekhovtsov.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAll();
    User create(User user);
    Optional<User> read(Long id);
    void update(User user);
    void delete(Long id);
}

