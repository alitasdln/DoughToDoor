package com.doughtodoor.user.controller;

import com.doughtodoor.user.model.User;
import com.doughtodoor.user.model.UserRole;
import com.doughtodoor.user.service.JpaUserDetailsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    private final JpaUserDetailsService jpaUserDetailsService;


    public UserController(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @GetMapping("/")
    public String home(){
        return "Hello Customer Page!";
    }

    @PostMapping("/users/register")
    public User newUser(@RequestBody User newUser) {
        newUser.setRoles(Collections.singleton(new UserRole("ROLE_USER")));
        return jpaUserDetailsService.registerUser(newUser);
    }

    //@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/users")
    public User getUserById(@RequestParam("id") Long userId) {
        return jpaUserDetailsService.getUserById(userId);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public List<User> listAllUsers() {
        return jpaUserDetailsService.getAllUsers();
    }

}
