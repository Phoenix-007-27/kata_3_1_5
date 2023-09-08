package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.UserNotFoundEx;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl
        implements UserService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, @Lazy PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userDao.showAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> optional = Optional.ofNullable(userDao.showById(id));
        return optional.orElseThrow(UserNotFoundEx::new);
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.create(user);
    }

    @Override
    public void update(int id, User newUser) {
        newUser.setId(id);
        if(!newUser.getPassword().equals(userDao.showById(id).getPassword())){
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
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
