package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    private final UserDetailsServiceImpl userDetailsService;

    public UserController(UserService userService, UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
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
        if (result.hasErrors()) {
            return "addUser";
        }

        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(Model model, @PathVariable("id") long id) {
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

//    @GetMapping("/userPage")
//    public String userPage() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        System.out.println(userDetails.getUser());
//
//        return "userPage";
//    }

    @GetMapping("/userPage")
    public String userPage(Model model, Principal principal) {
        String username = principal.getName();
        User user = (User) userDetailsService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "userPage";
    }

//    @GetMapping()
//    public String userPage(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User user = (User) userDetailsService.loadUserByUsername(userDetails.getUsername());
//        model.addAttribute("user", user);
//        return "user";
//    }

//    @GetMapping("/userPage")
//    public String userPage(Model model) {
//        model.addAttribute("user", userService.getUserById(model));
//
//        return "userPage";
//    }
}
