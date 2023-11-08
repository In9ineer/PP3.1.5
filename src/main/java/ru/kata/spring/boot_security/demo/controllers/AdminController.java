package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.validator.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

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

    @GetMapping(value = "/")
    public String getUsers(ModelMap model) {
        model.addAttribute("UserTable", userService.getUsers());

        return "getUsers";
    }

    @GetMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        return "addUser";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "addUser";
        }

        if (result.hasErrors()) {
            return "addUser";
        }

        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "editUser";
    }

    @PatchMapping("/{id}")
    public String update (@ModelAttribute("user") @Valid User user, BindingResult result, @PathVariable("id") long id) {
        if (result.hasErrors()) {
            return "editUser";
        }

        userService.update(id, user);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/userPage")
    public String userPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "userPage";
    }
}
