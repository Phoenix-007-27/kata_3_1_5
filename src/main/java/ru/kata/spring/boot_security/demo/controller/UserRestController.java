package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.mappers.UserMapper;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    @Autowired
    private UserRestController(UserService peopleServece) {
        this.userService = peopleServece;
    }

    @GetMapping("/users")
    public List<UserDTO> getAll() {
        return userService.findAll().stream().map(UserMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable("id") int id) {
        return UserMapper.INSTANCE.toDto(userService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody @Valid UserDTO userDTOtoCreate) {
        userService.save(userDTOtoCreate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody UserDTO userDTOtoUpdate) {
        userService.update(id, userDTOtoUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleEx(UserNotFoundException ex) {
        return new ResponseEntity<>("user not found" + System.currentTimeMillis(), HttpStatus.NOT_FOUND);
    }
}
