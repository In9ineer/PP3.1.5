package ru.kata.spring.boot_security.demo.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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

    @Override
    @Transactional
    public void update(long id, User updateUser) {
        userDao.save(updateUser);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDao.deleteById(id);
    }
}
