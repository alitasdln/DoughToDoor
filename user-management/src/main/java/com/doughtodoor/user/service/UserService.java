package com.doughtodoor.user.service;

import com.doughtodoor.user.exception.UserAlreadyExistsException;
import com.doughtodoor.user.exception.UserNotFoundException;
import com.doughtodoor.user.model.User;
import com.doughtodoor.user.model.UserRole;
import com.doughtodoor.user.repository.UserRepository;
import com.doughtodoor.user.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public User registerUser(User user) {
//        // Check if the username is already taken
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            throw new UserAlreadyExistsException("Username is already taken");
//        }
//
//        // Encode the password before saving it
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        // Set a default role for new users (e.g., "ROLE_USER")
//        UserRole defaultRole = userRoleRepository.findByName("ROLE_USER");
//        user.setRoles(Collections.singleton(defaultRole));
//
//        return userRepository.save(user);
//    }

    public User getCustomerById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }
}
