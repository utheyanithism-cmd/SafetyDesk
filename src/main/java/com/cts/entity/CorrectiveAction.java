package com.cts.entity;

import java.time.LocalDate;

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
public class CorrectiveAction {

    public enum StatusCategory {
        Open,
        InProgress,
        Completed,
        Overdue,
        Verified
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actionID;

    // Many corrective actions → one incident report (CAPA raised from an incident)
    @ManyToOne
    @JoinColumn(name = "incidentID", nullable = false)
    private IncidentReport incident;

    private String description;

    // Many corrective actions → assigned to one user
    @ManyToOne
    @JoinColumn(name = "assignedToID", nullable = false)
    private User assignedTo;

    private LocalDate dueDate;
    private LocalDate closedDate;

    // Many corrective actions → verified by one user (EHSManager / SafetyOfficer)
    @ManyToOne
    @JoinColumn(name = "verifiedByID")
    private User verifiedBy;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}