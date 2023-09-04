package ru.kata.spring.boot_security.demo.dao;




import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

     List<User> showAll();
     User showById(int id);
     User findByName(String username);

     void create(User user);
     void update(int id, User newPerson);
     void delete(int id);


}
