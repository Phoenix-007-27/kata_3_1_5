package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class PeopleController {

    private final UserService userService;

    @Autowired
    private PeopleController(UserService peopleServece) {
        this.userService = peopleServece;
    }

    @GetMapping("/admin")
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @GetMapping(value = "/user/{id}")
    public String findById(@PathVariable("id") int id, Model model, Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        model.addAttribute("user", userService.findById(id));
        if (authorities.stream().anyMatch(role -> "ROLE_ADMIN".equals(role.getAuthority())) || userService.findByName(username).getId() == id) {
            return "show";
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping(value = "/admin/create")
    public String form(@ModelAttribute("user") User user) {
        return "formTL";
    }


    @PostMapping(value = "/admin/create")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formTL";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {

        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";

    }
}
