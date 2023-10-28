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
        return userRepository
                .findByUsername(username)
                .map(User::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }

    @Transactional
    public User registerUser(User newUser) {
        // Encode the user's password
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // Query for the "ROLE_CUSTOMER" role by name
        Optional<UserRole> customerRoleOptional = userRoleRepository.findByName("ROLE_CUSTOMER");

        // If the role doesn't exist, you can handle it appropriately (e.g., throw an exception)
        UserRole customerRole = customerRoleOptional.orElseThrow(() -> new RuntimeException("ROLE_CUSTOMER does not exist"));

        // Set the user's roles
        newUser.setRoles(Collections.singleton(customerRole));

        // Save the user to the database
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
