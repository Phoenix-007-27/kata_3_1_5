package ru.kata.spring.boot_security.demo.services;




import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User findById(int id);

    User findByName(String name);

    void save(User user);

    void update(int id, User newUser);

    void delete(int id);

}
