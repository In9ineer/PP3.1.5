package ru.kata.spring.boot_security.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class RestUserController {

    private final UserServiceImpl userService;

    @Autowired
    public RestUserController(UserServiceImpl userService) {
        this.userService = userService;
    }

//    @GetMapping("/{username}")
//    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
//        User user = userService.getUserByUsername(username);
//        if (user != null) {
//            System.out.println("User found: " + user.toString());
//            return ResponseEntity.ok(user);
//        } else {
//            System.out.println("User not found for username: " + username);
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping()
    public ResponseEntity<User> getInfoUser(Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
