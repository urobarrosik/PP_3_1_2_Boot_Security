package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void add(User user);

    void addUser(User user);

    List<User> listUsers();

    User getUserById(Long id);

    void update(User user);

    void delete(Long id);

    User getUserByEmail(String email);
}
