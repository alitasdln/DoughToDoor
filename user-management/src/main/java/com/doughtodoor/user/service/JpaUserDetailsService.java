package com.doughtodoor.user.service;

import com.doughtodoor.user.exception.UserAlreadyExistsException;
import com.doughtodoor.user.exception.UserNotFoundException;
import com.doughtodoor.user.model.User;
import com.doughtodoor.user.model.UserRole;
import com.doughtodoor.user.repository.UserRepository;

import com.doughtodoor.user.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    public JpaUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to load user by username: " + username);

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getUsername());
            return new User(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRoles());

        } else {
            System.out.println("Username not found: " + username);
            throw new UsernameNotFoundException("Username not found: " + username);
        }
    }
    @Transactional
    public User registerUser(User newUser) {

        userRepository.findByUsername(newUser.getUsername())
                .ifPresent(existingUser -> {
                    throw new UserAlreadyExistsException("Username already exists.");
                });

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        UserRole customerRole = userRoleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new RuntimeException("ROLE_CUSTOMER does not exist"));
        // Set the user's roles
        newUser.setRoles(Collections.singleton(customerRole));

        return userRepository.save(newUser);
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
