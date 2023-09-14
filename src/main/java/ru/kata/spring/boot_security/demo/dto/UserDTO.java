package ru.kata.spring.boot_security.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kata.spring.boot_security.demo.model.Role;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int id;

    private String username;

    private String lastname;

    private String password;

    private String email;

    private int age;

    private Set<Role> roles;

    public Set<String> getRoles() {
        return roles.stream().map(Role::getRole).collect(Collectors.toSet());
    }

}
