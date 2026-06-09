package com.cts.entity;

import lombok.Data;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;

@Data
@Entity
public class Notification {

    public enum Category {
        Incident,
        Permit,
        Inspection,
        Health,
        Compliance,
        CAPA
    }

    public enum StatusCategory {
        Unread,
        Read,
        Dismissed
    }

    private int notificationID;
    private int userID;
    private String message;
    private Category category;
    private StatusCategory status;
    private LocalDateTime createdDate;
}