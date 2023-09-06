package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.Collection;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    private AdminController(UserService peopleServece, RoleService roleService) {
        this.userService = peopleServece;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String findAll(Model model, Principal principal) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("admin", userService.findByName(principal.getName()));
        model.addAttribute("roles", roleService.findAll());
        return "index";
    }

    @GetMapping("/admin/info")
    public String showAdminInfo(Model model, Principal principal) {
        model.addAttribute("adminUser", userService.findByName(principal.getName()));
        return "adminUser";
    }

    @GetMapping(value = "/admin/create")
    public String form(@ModelAttribute("user") User user, Model model, Principal principal) {
        model.addAttribute("admin", userService.findByName(principal.getName()));
         model.addAttribute("roles", roleService.findAll());


        return "formTL";
    }

    @PostMapping(value = "/admin/create")
    public String create(@ModelAttribute("user")  User user) {


        userService.save(user);
        return "redirect:/admin";
    }


    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";

    }
}