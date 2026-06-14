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
public class PermitExtension {

    public enum StatusCategory {
        Requested,
        Approved,
        Rejected
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer extensionID;

    // Many extensions → one work permit
    @ManyToOne
    @JoinColumn(name = "permitID", nullable = false)
    private WorkPermit permit;

    // Many extensions → requested by one user
    @ManyToOne
    @JoinColumn(name = "requestedByID", nullable = false)
    private User requestedBy;

    private LocalDateTime newEndDateTime;
    private String reason;

    // Many extensions → approved/rejected by one user (PTWCoordinator / EHSManager)
    @ManyToOne
    @JoinColumn(name = "approvedByID")
    private User approvedBy;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}