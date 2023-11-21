package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userDao;
    private RoleServise roleServise;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userDao, RoleServise roleServise, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleServise = roleServise;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        List<User> users = userDao.findAll();
        for (User user : users) {
            user.getRoles().size();
        }
        return users;
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Collections.singletonList(roleServise.getRoleById(1L))));

        userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userDao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    @Transactional
    public void update(User updateUser) {
        User existingUser = userDao.findById(updateUser.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(updateUser.getUsername());
//            existingUser.setPassword(updateUser.getPassword());
            existingUser.setEmail(updateUser.getEmail());
            userDao.save(existingUser);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDao.deleteById(id);
    }
}
