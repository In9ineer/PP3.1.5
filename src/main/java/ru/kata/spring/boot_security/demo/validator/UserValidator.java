package ru.kata.spring.boot_security.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Component
public class UserValidator implements Validator {

    UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    @Transactional
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        String username = user.getUsername();

        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            errors.rejectValue("username", "user.duplicateUsername", "This username is already taken");
        }
    }
}
