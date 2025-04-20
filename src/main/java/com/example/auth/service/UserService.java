package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.utils.JwtUtil;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void updateLoginInfo(User user) {
        user.setLastLogin(LocalDateTime.now());
        user.setLoginCount(user.getLoginCount() + 1);
        userRepository.save(user);
    }

    public void updateLogoutInfo(User user) {
        user.setLastLogout(LocalDateTime.now());
        userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String expectedPassword = hashPassword(password, user.getSalt());
        if (!expectedPassword.equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        user.setLastLogin(LocalDateTime.now());
        user.setLoginCount(user.getLoginCount() + 1);
        userRepository.save(user);

        // ✅ Extract username and roles to match JwtUtil method signature
        return jwtUtil.generateToken(user.getEmail(), user.getRoles());
    }

    private String hashPassword(String password, String salt) {
        return Base64.getEncoder().encodeToString((password + salt).getBytes());
    }
}
