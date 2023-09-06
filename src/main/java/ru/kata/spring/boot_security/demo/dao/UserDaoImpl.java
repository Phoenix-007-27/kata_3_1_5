package ru.kata.spring.boot_security.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class UserDaoImpl implements UserDao {

    private EntityManager entityManager;
    private RoleDaoImpl roleDao;

    @Autowired
    public UserDaoImpl(EntityManager entityManager, RoleDaoImpl roleDao) {
        this.entityManager = entityManager;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> showAll() {
        List<User> userList = entityManager.createQuery("select p from User p", User.class).getResultList();
        return userList;
    }

    @Override
    public User showById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(int id, User newUser) {
        User toUpdateUser = entityManager.find(User.class, id);
        toUpdateUser.setUsername(newUser.getUsername());
        toUpdateUser.setLastname(newUser.getLastname());
        toUpdateUser.setAge(newUser.getAge());
        toUpdateUser.setPassword(newUser.getPassword());
        toUpdateUser.setEmail(newUser.getEmail());
        toUpdateUser.setRoles(newUser.getRoles());
    }

    @Override
    public User findByName(String username) {

        Query query = entityManager.createQuery("Select u from User u left join fetch u.roles where u.username=:username");
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }
}
