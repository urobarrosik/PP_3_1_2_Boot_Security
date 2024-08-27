package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    void add(User user);

    void addUser(User user);

    List<User> listUsers();

    User getUserById(Long id);

    void update(User user);

    void delete(Long id);

    User getUserByEmail(String email);
}

