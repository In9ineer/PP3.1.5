package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleServise {

    void saveRole(Role role);

    Role getRoleById(Long id);

    void updateRole(Long id, Role role);

    void delite(Long id);
}
