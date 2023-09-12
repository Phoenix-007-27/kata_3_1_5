package ru.kata.spring.boot_security.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.dto.UserDTOtoCreate;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserErrorResponce;
import ru.kata.spring.boot_security.demo.util.UserNotFoundEx;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class MyRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    private MyRestController(UserService peopleServece, RoleService roleService) {
        this.userService = peopleServece;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<UserDTO> getAll() {
        return userService.findAll().stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable("id") int id) {
        return convertToUserDTO(userService.findById(id));
    }

    public UserDTO convertToUserDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> save(@RequestBody UserDTOtoCreate userDTOtoCreate) {
        userDTOtoCreate.setRoles(userDTOtoCreate.getRoles().stream().map(r -> roleService.findByName(r.getRole())).collect(Collectors.toSet()));
        userService.save(convertToUserCreate(userDTOtoCreate));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody UserDTOtoCreate userDTOtoCreate) {
//        userDTOtoCreate.setRoles(userDTOtoCreate.getRoles().stream().map(r -> roleService.findByName(r.getRole())).collect(Collectors.toSet()));
        userService.update(id, convertToUserCreate(userDTOtoCreate));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private User convertToUserCreate(UserDTOtoCreate userDTOtoCreate) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDTOtoCreate, User.class);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponce> handleEx(UserNotFoundEx ex) {
        UserErrorResponce responce = new UserErrorResponce(
                "user not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(responce, HttpStatus.NOT_FOUND);
    }
}
