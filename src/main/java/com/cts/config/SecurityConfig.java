package com.cts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.security.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // enables @PreAuthorize where URL rules aren't expressive enough
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final com.cts.security.RestAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // REST API: no CSRF tokens, no server-side sessions
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    // --- public ---
                    .requestMatchers("/api/auth/**", "/api/health").permitAll()

                    // --- Swagger / OpenAPI docs (public) ---
                    .requestMatchers(
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/v3/api-docs",
                            "/v3/api-docs/**",
                            "/v3/api-docs.yaml"
                    ).permitAll()

                    //User management
                    .requestMatchers(HttpMethod.POST,   "/api/users").hasAnyRole("ADMIN", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,    "/api/users/**").hasAnyRole("ADMIN", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole("ADMIN", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,    "/api/users/**").authenticated()

                    //Audit
                    .requestMatchers("/api/audit/**").hasAnyRole("EHS_MANAGER", "COMPLIANCE_OFFICER", "ADMIN")

                    // --- Incident 
                    .requestMatchers(HttpMethod.POST, "/api/incidents").authenticated()
                    .requestMatchers(HttpMethod.PUT,  "/api/incidents/*/assign-investigator")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/incidents/*/status")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/incidents/**").authenticated()

                    //Investigation
                    .requestMatchers(HttpMethod.POST, "/api/investigations/**")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/investigations/**")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/investigations/**").authenticated()

                    //Corrective Action
                    .requestMatchers(HttpMethod.POST, "/api/corrective-actions/**")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/corrective-actions/**")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/corrective-actions/**").authenticated()

                    //Hazard
                    .requestMatchers(HttpMethod.POST, "/api/hazards")
                            .hasAnyRole("EMPLOYEE", "SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/hazards/*/status")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/hazards/**").authenticated()

                    //Risk Assessment
                    .requestMatchers(HttpMethod.POST, "/api/risk-assessments/**")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/risk-assessments/**")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/risk-assessments/**").authenticated()

                    //Inspection
                    .requestMatchers(HttpMethod.POST, "/api/inspections/**")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/inspections/**")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/inspections/**").authenticated()

                    //Finding
                    .requestMatchers(HttpMethod.POST, "/api/findings/**")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/findings/**")
                            .hasAnyRole("SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/findings/**").authenticated()

                    //Permit
                    .requestMatchers(HttpMethod.POST, "/api/permits/**")
                            .hasAnyRole("PTW_COORDINATOR", "SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/permits/**")
                            .hasAnyRole("PTW_COORDINATOR", "SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/permits/**").authenticated()

                    //Permit Extension
                    .requestMatchers(HttpMethod.POST, "/api/extensions/**")
                            .hasAnyRole("PTW_COORDINATOR", "SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/extensions/**")
                            .hasAnyRole("PTW_COORDINATOR", "SAFETY_OFFICER", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/extensions/**").authenticated()

                    //Health
                    .requestMatchers(HttpMethod.POST, "/api/health-records/**").hasAnyRole("OH_NURSE", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/health-records/**").hasAnyRole("OH_NURSE", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/health-records/**").authenticated()

                    //Referral
                    .requestMatchers(HttpMethod.POST, "/api/referrals/**").hasAnyRole("OH_NURSE", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.PUT,  "/api/referrals/**").hasAnyRole("OH_NURSE", "EHS_MANAGER")
                    .requestMatchers(HttpMethod.GET,  "/api/referrals/**").authenticated()

                    //Notifications
                    .requestMatchers("/api/notifications/**").authenticated()

                    //Analytics
                    .requestMatchers("/api/analytics/**")
                            .hasAnyRole("EHS_MANAGER", "COMPLIANCE_OFFICER", "ADMIN")

                    .anyRequest().authenticated())
            // Return 401 when authentication is missing/invalid
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                    .accessDeniedHandler(accessDeniedHandler))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}