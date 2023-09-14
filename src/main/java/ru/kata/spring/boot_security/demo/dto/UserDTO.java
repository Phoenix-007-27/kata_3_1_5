package ru.kata.spring.boot_security.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int id;

    @NotEmpty(message = "username shouldn`t be empty ")
    private String username;

    @NotEmpty(message = "lastname shouldn`t be empty ")
    private String lastname;

    @NotEmpty(message = "password shouldn`t be empty ")
    private String password;

    @NotEmpty(message = "email shouldn`t be empty ")
    private String email;

    @Min( value = 2, message = "age should be more than 2")
    private int age;

    private Set<Role> roles;

    public Set<String> getRoles() {
        return roles.stream().map(Role::getRole).collect(Collectors.toSet());
    }

}
