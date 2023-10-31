package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/")
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


}
