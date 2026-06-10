package com.cts.service;

import com.cts.dto.UserRequestDTO;
import com.cts.dto.UserResponseDTO;
import com.cts.entity.AuditLog;
import com.cts.entity.User;
import com.cts.entity.User.StatusCategory;
import com.cts.exception.DuplicateEmailException;
import com.cts.exception.UserNotFoundException;
import com.cts.repository.AuditLogRepository;
import com.cts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;

    // CREATE
    public UserResponseDTO registerUser(UserRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already registered: " + request.getEmail());
        }
        User user = mapToEntity(request);
        user.setStatus(StatusCategory.Active);
        User saved = userRepository.save(user);
        logAudit(saved.getUserID(), "USER_REGISTERED", "User", saved.getUserID());
        return mapToResponse(saved);
    }

    // READ ALL
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // READ ONE
    public UserResponseDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return mapToResponse(user);
    }

    // UPDATE
    public UserResponseDTO updateUser(int id, UserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setPhone(request.getPhone());
        user.setSiteID(request.getSiteID());
        user.setDepartmentID(request.getDepartmentID());
        if (request.getStatus() != null) user.setStatus(request.getStatus());
        User updated = userRepository.save(user);
        logAudit(id, "USER_UPDATED", "User", id);
        return mapToResponse(updated);
    }

    // SOFT DELETE (deactivation)
    public void deactivateUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        user.setStatus(StatusCategory.Inactive);
        userRepository.save(user);
        logAudit(id, "USER_DEACTIVATED", "User", id);
    }

    // AUDIT LOGGER
    private void logAudit(int userID, String action, String entityType, int recordID) {
        AuditLog log = new AuditLog();
        log.setUserID(userID);
        log.setAction(action);
        log.setEntityType(entityType);
        log.setRecordID(recordID);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    // MAPPER: Request → Entity
    private User mapToEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setSiteID(dto.getSiteID());
        user.setDepartmentID(dto.getDepartmentID());
        user.setPassword(dto.getPassword());
        return user;
    }

    // MAPPER: Entity → Response
    private UserResponseDTO mapToResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserID(user.getUserID());
        dto.setName(user.getName());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setSiteID(user.getSiteID());
        dto.setDepartmentID(user.getDepartmentID());
        dto.setStatus(user.getStatus());
        return dto;
    }
}