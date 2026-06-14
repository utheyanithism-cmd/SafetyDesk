package com.cts.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Notification {

    public enum Category {
        Incident, Permit, Inspection, Health, Compliance, CAPA
    }

    public enum StatusCategory {
        Unread, Read, Dismissed
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationID;

    // Many notifications → sent to one user
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    private String message;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;

    private LocalDateTime createdDate;
}