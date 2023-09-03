package ru.kata.spring.boot_security.demo.dao;




import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

     List<User> showAll();
     User showById(int id);
     void create(User user);
     void update(int id, User newPerson);
     void delete(int id);


}
