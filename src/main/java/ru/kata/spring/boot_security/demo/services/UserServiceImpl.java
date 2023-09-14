package ru.kata.spring.boot_security.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.mappers.UserMapper;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl
        implements UserService {

    private UserDao userDao;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService, @Lazy PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userDao.showAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> optional = Optional.ofNullable(userDao.showById(id));
        return optional.orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void save(UserDTO userDTO) {
        Set<Role> rolesToSave = userDTO.getRoles().stream().map(r -> roleService.findByName(r)).collect(Collectors.toSet());
        User userToSave = UserMapper.INSTANCE.toUser(userDTO);
        userToSave.setRoles(rolesToSave);
        userToSave.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDao.create(userToSave);
    }

    @Override
    public void update(int id, UserDTO userDTO) {
        Set<String> roles = userDTO.getRoles();
        User existingUser = UserMapper.INSTANCE.toUser(userDTO);
        existingUser.setId(id);
        existingUser.setRoles(roles.stream().map(r -> roleService.findByName(r)).collect(Collectors.toSet()));
        if (!existingUser.getPassword().equals(userDao.showById(id).getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        }
        userDao.update(id, existingUser);
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
