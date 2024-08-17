package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('users:read')")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('users:write')")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.listUsers());
            return "users";
        }
        userService.add(user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('users:write')")
    public String deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('users:write')")
    public String editUser(@RequestParam Long id, Model model) {
        System.out.println(id);
        model.addAttribute("editsUser", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('users:write')")
    public String updateUser(@ModelAttribute("editsUser") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editsUser", user);
            return "edit";
        }
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        userService.update(user);
        return "redirect:/users";
    }

}
