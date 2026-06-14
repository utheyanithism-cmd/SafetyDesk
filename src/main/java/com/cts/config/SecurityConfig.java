package com.cts.config;

import com.cts.entity.User;
import com.cts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

    // ✅ STEP 1 — Load user + role from YOUR database
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
                .accountLocked(user.getStatus() ==
                    User.StatusCategory.Inactive)
                .disabled(user.getStatus() ==
                    User.StatusCategory.Inactive)
                .build();
        };
    }

    // ✅ STEP 2 — Wire DB loader + BCrypt together
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // ✅ STEP 3 — Expose AuthenticationManager for login use
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ✅ STEP 4 — BCrypt password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ STEP 5 — URL rules per SafetyDesk role
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth

                // ── PUBLIC ──────────────────────────────────────
                .requestMatchers(
                    "/api/users/register",
                    "/api/users/login",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html"
                ).permitAll()

                // ── IDENTITY & ACCESS ────────────────────────────
                // Only Admin and EHSManager can manage users
                .requestMatchers("/api/users/**")
                    .hasAnyRole("Admin", "EHSManager")

                // Only Admin can view audit logs
                .requestMatchers("/api/audit/**")
                    .hasRole("Admin")

                // ── INCIDENT MODULE ──────────────────────────────
                // Employee, SafetyOfficer, EHSManager can CREATE
                .requestMatchers(
                    org.springframework.http.HttpMethod.POST,
                    "/api/incidents/**")
                    .hasAnyRole("Employee", "SafetyOfficer",
                                "EHSManager", "Admin")

                // SafetyOfficer, EHSManager, ComplianceOfficer can READ
                .requestMatchers(
                    org.springframework.http.HttpMethod.GET,
                    "/api/incidents/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager",
                                "ComplianceOfficer", "Admin")

                // SafetyOfficer, EHSManager can UPDATE
                .requestMatchers(
                    org.springframework.http.HttpMethod.PUT,
                    "/api/incidents/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager", "Admin")

                // ── INVESTIGATION & CAPA ─────────────────────────
                .requestMatchers("/api/investigations/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager", "Admin")

                .requestMatchers("/api/corrective-actions/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager", "Admin")

                // ── HAZARD & RISK ────────────────────────────────
                .requestMatchers("/api/hazards/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager", "Admin")

                .requestMatchers("/api/risk-assessments/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager", "Admin")

                // ── INSPECTION ───────────────────────────────────
                .requestMatchers("/api/inspections/**")
                    .hasAnyRole("SafetyOfficer", "ComplianceOfficer",
                                "EHSManager", "Admin")

                .requestMatchers("/api/inspection-findings/**")
                    .hasAnyRole("SafetyOfficer", "EHSManager", "Admin")

                // ── PERMIT TO WORK ───────────────────────────────
                .requestMatchers("/api/permits/**")
                    .hasAnyRole("PTWCoordinator", "EHSManager", "Admin")

                // ── OCCUPATIONAL HEALTH ──────────────────────────
                .requestMatchers("/api/health/**")
                    .hasAnyRole("OHNurse", "EHSManager", "Admin")

                .requestMatchers("/api/referrals/**")
                    .hasAnyRole("OHNurse", "EHSManager", "Admin")

                // ── EHS REPORTS ──────────────────────────────────
                .requestMatchers("/api/reports/**")
                    .hasAnyRole("EHSManager", "ComplianceOfficer", "Admin")

                // ── NOTIFICATIONS ────────────────────────────────
                // Any logged-in user can see their notifications
                .requestMatchers("/api/notifications/**")
                    .authenticated()

                // Everything else needs login
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}