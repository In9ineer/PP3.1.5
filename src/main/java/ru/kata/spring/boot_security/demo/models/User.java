package ru.kata.spring.boot_security.demo.models;

//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 30, message = "The username must be between 2 and 30 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "Username should only contain letters")
    private String username;

    @Column(name = "password")
    @Size(min = 6, max = 12, message = "The password must be between 6 and 12 characters")
    private String password;

    @Column(name = "email")
    @Email(message = "Invalid email format")
    private String email;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
