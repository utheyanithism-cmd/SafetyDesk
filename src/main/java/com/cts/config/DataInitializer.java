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
            // FIX: check by email instead of count()
            // count() == 0 fails if other users exist but admin was never seeded
            // findByEmail ensures we only skip if THIS specific admin already exists
            if (userRepository.findByEmail("a@a.com").isEmpty()) {
                User admin = new User();
                admin.setName("Admin User");
                admin.setRole(User.RoleCategory.Admin);
                admin.setEmail("a@a.com");
                admin.setPhone("9999999999");
                admin.setSiteID(1);
                admin.setDepartmentID(1);
                admin.setStatus(User.StatusCategory.Active);
                // passwordEncoder is fully initialized here — BCrypt hash is safe
                admin.setPassword(passwordEncoder.encode("admin"));
                userRepository.save(admin);
                System.out.println(
                    "✅ Default admin created: a@a.com / admin");
            } else {
                System.out.println(
                    "ℹ️ Admin already exists, skipping seed.");
            }
        };
    }
}