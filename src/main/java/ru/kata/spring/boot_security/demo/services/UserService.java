package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(int id);

    User findByName(String name);

    void save(UserDTO user);

    void update(int id, UserDTO newUser);

    void delete(int id);

}
