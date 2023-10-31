package ru.kata.spring.boot_security.demo.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rolename", nullable = false)
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 30, message = "The username must be between 2 and 30 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "Username should only contain letters")
    private String rolename;


}
