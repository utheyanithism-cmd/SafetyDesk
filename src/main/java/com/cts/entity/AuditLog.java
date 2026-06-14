package com.cts.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auditID;

    // Many audit log entries → one user who performed the action
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    private String action;
    private String entityType;
    private Integer recordID;
    private LocalDateTime timestamp;
}