package ru.kata.spring.boot_security.demo.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;
import ru.kata.spring.boot_security.demo.validator.UserValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restAdmin")
public class RestAdmin {

    private final UserService userService;
    private final UserServiceImpl userServiceImpl;
    private final UserValidator userValidator;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public RestAdmin(UserService userService, UserServiceImpl userServiceImpl, UserValidator userValidator, UserDetailsServiceImpl userDetailsService) {
        this.userService = userService;
        this.userServiceImpl = userServiceImpl;
        this.userValidator = userValidator;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getOneUser (@PathVariable("id") long id) {
        return userServiceImpl.findOne(id);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            return ResponseEntity.badRequest().body(errors);
        }

        userService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            return ResponseEntity.badRequest().body(errors);
        }
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable long id) {
//        User user = userService.getUserById(id);
//        if (user==null) {
//            throw new Exception("user with this ID not found");
//        }

        userService.delete(id);
        return "user with ID = " + id + " deleted";
    }
}
