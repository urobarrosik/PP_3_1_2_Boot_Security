package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String getUsersForAdmin(Model model, Principal principal) {
        model.addAttribute("addedUser", new User());
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("admin", userService.getUserByEmail(principal.getName()));
        model.addAttribute("editsUser", new User());
        return "admin";
    }

}