package ru.kata.spring.boot_security.demo.Dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getUsers() {
        return entityManager.createQuery("select p from User p", User.class)
                .getResultList();
    }

    public void save(User user) {
        entityManager.persist(user);
    }

    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    public void update(long id, User updateUser) {
        User userToBeUpdate = entityManager.find(User.class, id);

        userToBeUpdate.setUsername(updateUser.getUsername());
        userToBeUpdate.setPassword(updateUser.getPassword());
        userToBeUpdate.setEmail(updateUser.getEmail());
    }

    public void delete(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

}
