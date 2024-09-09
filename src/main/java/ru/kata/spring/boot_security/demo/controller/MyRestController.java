package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class MyRestController {

    private final UserService userService;
    private final RoleService roleService;

    public MyRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/rest_users")
    public List<User> getUsers() {
        return userService.listUsers();
    }

    @GetMapping("/rest_users/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/rest_users")
    public User addUser(@RequestBody User user) {
        System.out.println("Adding user: " + user.getFirstName());
        userService.addUser(user);
        return user;
    }

    @PutMapping("/rest_users")
    public User updateUser(@RequestBody User user) {
        userService.update(user);
        return user;
    }

    @DeleteMapping("/rest_users/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.delete(id);
    }

    @GetMapping("/roles")
    public Collection<Role> getRoles() {
        return roleService.findAllRoles();
    }

    @GetMapping("/user/current")
    public User getCurrentUser(Principal principal) {
        return userService.getUserByEmail(principal.getName());
    }
}