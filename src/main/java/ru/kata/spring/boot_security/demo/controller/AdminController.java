package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
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
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("addedUser") @Valid User addedUser, BindingResult bindingResult, Model model
            , Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.listUsers());
            model.addAttribute("roles", roleService.findAllRoles());
            model.addAttribute("admin", userService.getUserByEmail(principal.getName()));
            return "admin";
        }
        userService.addUser(addedUser);
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
        model.addAttribute("roles", roleService.findAllRoles());
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
