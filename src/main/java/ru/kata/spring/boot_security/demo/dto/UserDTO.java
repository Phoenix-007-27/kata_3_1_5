package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {


    private int id;
    private String username;
    private String lastname;
    private String email;
    private int age;
    private Set<Role> roles;


    public UserDTO(int id, String username, String lastname, String email, int age, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.age = age;
        this.roles = roles;
    }

    public UserDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id && age == userDTO.age && Objects.equals(username, userDTO.username) && Objects.equals(lastname, userDTO.lastname) && Objects.equals(email, userDTO.email) && Objects.equals(roles, userDTO.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, lastname, email, age, roles);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<String> getRoles() {
        return roles.stream().map(Role::getRole).collect(Collectors.toSet());
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
