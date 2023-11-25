package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.validator.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping()
    public String getUsers(ModelMap model, Principal principal) {
        String username = principal.getName();
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("UserTable", userService.getUsers());
        model.addAttribute("user", user);
        return "admin/getUsers";
    }

    @GetMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        return "admin/getUsers";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("f") @Valid User user, BindingResult result) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "admin/getUsers";
        }

        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/getUsers";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult result, @PathVariable("id") long id) {
        if (result.hasErrors()) {
            return "admin/getUsers";
        }

        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
