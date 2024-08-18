package ru.shekhovtsov.service;


import org.springframework.stereotype.Service;
import ru.shekhovtsov.dao.UserDao;
import ru.shekhovtsov.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(User user) {
        userDao.create(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userDao.read(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
