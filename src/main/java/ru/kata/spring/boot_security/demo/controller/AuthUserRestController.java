package ru.kata.spring.boot_security.demo.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@RestController
public class AuthUserRestController {

    private final UserService userService;

    @Autowired
    public AuthUserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/auth")
    public UserDTO getAuthUser(Principal principal) {
        return convertToUserDTO(userService.findByName(principal.getName()));
    }

    public UserDTO convertToUserDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }
}