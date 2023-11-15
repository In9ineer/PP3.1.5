package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

@Service
public class RoleServiseImpl implements RoleServise {

    RoleRepository roleRepository;

    @Autowired
    public RoleServiseImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    @Override
    public Role getRoleById(Long id) {
        return roleRepository.getById(id);
    }

    @Transactional
    @Override
    public void updateRole(Long id, Role role) {
        roleRepository.save(role);
    }

    @Transactional
    @Override
    public void delite(Long id) {
        roleRepository.deleteById(id);
    }
}
