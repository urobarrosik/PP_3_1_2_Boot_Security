package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    @GetMapping("/admin")
    public String getUsersForAdmin(Model model) {
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.listUsers());
            return "admin";
        }
        model.addAttribute("user", user);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


    @PostMapping("/edit")
    public String editUser(@RequestParam Long id, Model model) {
        model.addAttribute("editsUser", userService.getUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "edit";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("editsUser") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editsUser", user);
            return "edit";
        }
        userService.update(user);
        return "redirect:/admin";
    }

}
