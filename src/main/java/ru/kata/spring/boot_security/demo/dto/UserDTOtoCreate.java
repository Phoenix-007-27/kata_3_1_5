package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTOtoCreate {


    private String username;
    private String lastname;
    private String password;
    private String email;
    private int age;
    private Set<Role> roles;


    public UserDTOtoCreate(String username, String lastname, String password, String email, int age, Set<Role> roles) {

        this.username = username;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.age = age;
        this.roles = roles;
    }

    public UserDTOtoCreate() {
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTOtoCreate that = (UserDTOtoCreate) o;
        return age == that.age && Objects.equals(username, that.username) && Objects.equals(lastname, that.lastname) && Objects.equals(password, that.password) && Objects.equals(email, that.email) && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, lastname, password, email, age, roles);
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


    @Override
    public String toString() {
        return "UserDTOtoCreate{" +

                ", username='" + username + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", roles=" + roles +
                '}';
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
