package ru.kata.spring.boot_security.demo.model;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;


@Component
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Поле имени не должно быть пустым")
    @Size(min = 2, max = 30, message = "Такие имена не поддерживаются")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Поле фамилии не должно быть пустым")
    @Size(min = 1, max = 50, message = "Такие фамилии не поддерживаются")
    private String lastName;

    @Column(name = "email", unique = true)
    @Email(message = "Это не Email")
    private String username; //EMAIL

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "users_role")
    @Column(name = "role")
    private Collection<Role> roles;

    public User() {}

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + username + '\'' +
                '}';
    }
}

