package ru.kata.spring.boot_security.demo.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl
        implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() {
        return userDao.showAll();
    }
    @Override
    public User findById(int id) {
        Optional<User> optional = Optional.ofNullable(userDao.showById(id));
        return optional.orElse(null);
    }

    @Override
    public void save(User user) {
        userDao.create(user);
    }

    @Override
    public void update(int id, User newUser) {
        newUser.setId(id);
        userDao.update(id, newUser);
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }


}
