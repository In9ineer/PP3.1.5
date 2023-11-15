package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rolename", nullable = false)
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 30, message = "The username must be between 2 and 30 characters")
    private String rolename;

    @ManyToMany(mappedBy = "roles")
    @Transient
    private Set<User> users;

    public Role() {
    }

    public Role(String rolename) {
        this.rolename = rolename;
    }

    public Role(Long id, String rolename, Set<User> users) {
        this.id = id;
        this.rolename = rolename;
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getRolename();
    }
}
