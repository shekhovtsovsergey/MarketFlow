package ru.shekhovtsov.service;


import ru.shekhovtsov.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public void createUser(User user);
    public Optional<User> getUserById(Long id);
    public List<User> getAllUsers();
    public void deleteUser(Long id);

}
