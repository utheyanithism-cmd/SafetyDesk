package com.cts.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.dto.request.LoginRequest;
import com.cts.dto.request.RefreshRequest;
import com.cts.dto.response.LoginResponse;
import com.cts.entity.User;
import com.cts.enums.UserStatus;
import com.cts.exception.AccessForbiddenException;
import com.cts.exception.InvalidCredentialsException;
import com.cts.repository.UserRepository;
import com.cts.security.JwtService;
import com.cts.service.AuditLogService;
import com.cts.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuditLogService auditLogService;

    private static final String ENTITY_TYPE = "User";

    @Value("${safetydesk.security.max-failed-attempts}")
    private int maxFailedAttempts;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        log.info("Login attempt for {}", request.getEmail());

     User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.warn("Login failed - unknown email: {}", request.getEmail());
                    return new InvalidCredentialsException("Invalid email or password");
                });
     if (user.isAccountLocked()) {
            auditLogService.record(user.getUserId(), "FailedLogin", ENTITY_TYPE, user.getUserId());
            throw new AccessForbiddenException(
                    "Account is locked due to too many failed login attempts. Contact your administrator.");
        }
   if (user.getStatus() == UserStatus.INACTIVE || user.getStatus() == UserStatus.TRANSFERRED) {
            auditLogService.record(user.getUserId(), "FailedLogin", ENTITY_TYPE, user.getUserId());
            throw new AccessForbiddenException(
                    "Account is " + user.getStatus().getLabel() + " and cannot log in.");
        }

      if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            handleFailedAttempt(user);
            throw new InvalidCredentialsException("Invalid email or password");
        }


        if (user.getFailedLoginAttempts() != 0) {
            user.setFailedLoginAttempts(0);
            userRepository.save(user);
        }
        auditLogService.record(user.getUserId(), "Login", ENTITY_TYPE, user.getUserId());

        log.info("Login successful for userId={}", user.getUserId());
        return buildTokenResponse(user);
    }

    @Override
    @Transactional
    public LoginResponse refresh(RefreshRequest request) {
        String token = request.getRefreshToken();

  
        if (!jwtService.isTokenValid(token) || !"REFRESH".equals(jwtService.extractTokenType(token))) {
            throw new InvalidCredentialsException("Invalid or expired refresh token");
        }

        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid refresh token"));

        if (user.isAccountLocked()
                || user.getStatus() == UserStatus.INACTIVE
                || user.getStatus() == UserStatus.TRANSFERRED) {
            throw new AccessForbiddenException("Account is not permitted to refresh a session.");
        }

        log.info("Token refreshed for userId={}", user.getUserId());
        return buildTokenResponse(user);
    }

    	private void handleFailedAttempt(User user) {
        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);

        if (attempts >= maxFailedAttempts) {
            user.setAccountLocked(true);
            log.warn("Account locked after {} failed attempts: userId={}", attempts, user.getUserId());
        }
        userRepository.save(user);

       auditLogService.record(user.getUserId(), "FailedLogin", ENTITY_TYPE, user.getUserId());
    }

    	private LoginResponse buildTokenResponse(User user) {
    	    long expirationMs = jwtService.getAccessExpirationMs();
    	    long expirationMin = expirationMs / (1000 * 60);

    	    String expiresInString = expirationMin + " min";

    	    return LoginResponse.builder()
    	            .accessToken(jwtService.generateAccessToken(user))
    	            .refreshToken(jwtService.generateRefreshToken(user))
    	            .tokenType("Bearer")
    	            .userId(user.getUserId())
    	            .role(user.getRole())
    	            .siteId(user.getSiteId())
    	            .expiresIn(expiresInString) // <-- Pass the string here instead of the raw long
    	            .build();
    	}
}