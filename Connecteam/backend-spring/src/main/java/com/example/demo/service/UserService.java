package com.example.demo.service;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    public User save(User user) { return userRepository.save(user); }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities("USER")
            .build();
    }
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
} 