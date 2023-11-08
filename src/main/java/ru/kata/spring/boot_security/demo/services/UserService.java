package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    void save(User user);

    User getUserById(Long id);

    public User getUserByUsername(String username);

    void update(long id, User updateUser);

    void delete(long id);
}
