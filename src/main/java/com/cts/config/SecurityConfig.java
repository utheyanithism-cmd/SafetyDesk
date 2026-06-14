package com.cts.config;

import com.cts.entity.User;
import com.cts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    // ✅ BEAN 1 — Loads user + role from YOUR database
    // Spring Boot auto-detects this and uses it for authentication
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                    "No user found with email: " + email));

            return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority(
                    "ROLE_" + user.getRole().name()))
                .accountLocked(
                    user.getStatus() == User.StatusCategory.Inactive)
                .disabled(
                    user.getStatus() == User.StatusCategory.Inactive)
                .build();
        };
    }

    // ✅ BEAN 2 — BCrypt password encoder
    // Spring Boot auto-detects this and uses it to verify passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ BEAN 3 — AuthenticationManager
    // Used in UserService login method
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ✅ BEAN 4 — URL-level role restrictions
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // ── PUBLIC — no login needed ─────────────────────
                .requestMatchers(
                    "/api/users/register",
                    "/api/users/login",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**"
                ).permitAll()

                // ── IDENTITY & ACCESS ────────────────────────────
                // Admin and EHSManager can manage users
                .requestMatchers("/api/users/**")
                    .hasAnyRole("Admin", "EHSManager")

                // Only Admin, EHSManager, ComplianceOfficer
                // can view audit logs
                .requestMatchers("/api/audit/**")
                    .hasAnyRole("Admin", "EHSManager",
                                "ComplianceOfficer")

                // ── INCIDENT MODULE ──────────────────────────────
                // Employee, SafetyOfficer, EHSManager can CREATE
                .requestMatchers(
                    org.springframework.http.HttpMethod.POST,
                    "/api/incidents/**")
                    .hasAnyRole("Employee", "SafetyOfficer",
                                "EHSManager", "Admin")

                // SafetyOfficer, EHSManager,
                // ComplianceOfficer can READ
                .requestMatchers(
                    org.springframework.http.HttpMethod.GET,
                    "/api/incidents/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager",
                                "ComplianceOfficer", "Admin")

                // SafetyOfficer, EHSManager can UPDATE
                .requestMatchers(
                    org.springframework.http.HttpMethod.PUT,
                    "/api/incidents/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager",
                                "Admin")

                // ── INVESTIGATION & CAPA ─────────────────────────
                .requestMatchers("/api/investigations/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager",
                                "Admin")

                .requestMatchers("/api/corrective-actions/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager",
                                "Admin")

                // ── HAZARD & RISK ────────────────────────────────
                .requestMatchers("/api/hazards/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager",
                                "Admin")

                .requestMatchers("/api/risk-assessments/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager",
                                "Admin")

                // ── INSPECTION ───────────────────────────────────
                .requestMatchers("/api/inspections/**")
                    .hasAnyRole("SafetyOfficer", "ComplianceOfficer",
                                "EHSManager", "Admin")

                .requestMatchers("/api/inspection-findings/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager",
                                "Admin")

                // ── PERMIT TO WORK ───────────────────────────────
                .requestMatchers("/api/permits/**")
                    .hasAnyRole("PTWCoordinator", "EHSManager",
                                "Admin")

                // ── OCCUPATIONAL HEALTH ──────────────────────────
                .requestMatchers("/api/health/**")
                    .hasAnyRole("OHNurse", "EHSManager", "Admin")

                .requestMatchers("/api/referrals/**")
                    .hasAnyRole("OHNurse", "EHSManager", "Admin")

                // ── EHS REPORTS ──────────────────────────────────
                .requestMatchers("/api/reports/**")
                    .hasAnyRole("EHSManager", "ComplianceOfficer",
                                "Admin")

                // ── NOTIFICATIONS ────────────────────────────────
                // Any logged-in user
                .requestMatchers("/api/notifications/**")
                    .authenticated()

                // Everything else needs login
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}