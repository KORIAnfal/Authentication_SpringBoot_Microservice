package com.example.auth; // adjust this to your package structure

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@example.com");

                String salt = "salt123";
                admin.setSalt(salt);
                admin.setPassword(hashPassword("admin123", salt));
                admin.setRoles(List.of("ROLE_ADMIN", "ROLE_USER"));
                admin.setLoginCount(0);

                userRepository.save(admin);
            }

            if (userRepository.findByEmail("user@example.com").isEmpty()) {
                User user = new User();
                user.setEmail("user@example.com");

                String salt = "salt456";
                user.setSalt(salt);
                user.setPassword(hashPassword("user123", salt));
                user.setRoles(List.of("ROLE_USER"));
                user.setLoginCount(0);

                userRepository.save(user);
            }
        };
    }

    private String hashPassword(String password, String salt) {
        return Base64.getEncoder().encodeToString((password + salt).getBytes());
    }
}
