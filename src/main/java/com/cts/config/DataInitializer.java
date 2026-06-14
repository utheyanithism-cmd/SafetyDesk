package com.cts.config;

import com.cts.entity.User;
import com.cts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Only create if no users exist yet
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setName("Admin User");
                admin.setRole(User.RoleCategory.Admin);
                admin.setEmail("admin@s.com");
                admin.setPhone("9999999999");
                admin.setSiteID(1);
                admin.setDepartmentID(1);
                admin.setStatus(User.StatusCategory.Active);
                admin.setPassword(
                    passwordEncoder.encode("admin"));
                userRepository.save(admin);
                System.out.println(
                    "✅ Default admin created: " +
                    "admin@safetydesk.com / admin123");
            }
        };
    }
}