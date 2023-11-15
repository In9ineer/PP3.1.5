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

    private final UserRepository userDao;
    private final RoleServise roleServise;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userDao, RoleServise roleServise, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userDao = userDao;
        this.roleServise = roleServise;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userDao.findAll();
    }

//    @Override
//    @Transactional
//    public void save(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        userDao.save(user);
//    }

//    @Override
//    @Transactional
//    public void save(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
////        Set<Role> roles = new HashSet<>();
////        roles.add(new Role("USER"));
//        user.setRoles((Set<Role>) roleServise.getRoleById(1L));
//
//        userDao.save(user);
//    }

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

//    @Override
//    @Transactional(readOnly = true)
//    public User getUserById(long id) {
//        User user = userDao.getById(id);
//        Hibernate.initialize(user);
//        return user;
//    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) { return userDao.findUserByUsername(username); }

//    @Override
//    @Transactional(readOnly = true)
//    public User getUserByUsername(String username) {
//        User user = userDao.findUserByUsername(username);
//        Hibernate.initialize(user);
//        return user;
//    }

//    @Override
//    @Transactional
//    public void update(long id, User updateUser) {
//        userDao.save(updateUser);
//    }

    @Override
    @Transactional
    public void update(User updateUser) {
        User existingUser = userRepository.findById(updateUser.getId()).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(updateUser.getUsername());
            existingUser.setPassword(updateUser.getPassword());
            existingUser.setEmail(updateUser.getEmail());
            userRepository.save(existingUser);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDao.deleteById(id);
    }
}
