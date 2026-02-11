package com.example.demo.service;

import com.example.demo.model.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;


public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    public Register register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
           return new Register("Пользователь с таким именем уже зарегистрирован");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        User userNew = userRepository.save(user);
        return new Register(true);

    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username){
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        return new User();
    }

    public User save(User user){
        return userRepository.save(user);
    }

}