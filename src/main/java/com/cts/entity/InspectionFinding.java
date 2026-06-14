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
public class InspectionFinding {

    public enum FindingType {
        NonConformance,
        Observation,
        BestPractice
    }

    public enum RiskLevel {
        Low,
        Medium,
        High,
        Critical
    }

    public enum StatusCategory {
        Open,
        InProgress,
        Closed,
        Overdue
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer findingID;

    // Many findings → one inspection schedule
    @ManyToOne
    @JoinColumn(name = "scheduleID", nullable = false)
    private InspectionSchedule schedule;

    @Enumerated(EnumType.STRING)
    private FindingType findingType;

    private String description;
    private String location;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    // Many findings → assigned to one user for resolution
    @ManyToOne
    @JoinColumn(name = "assignedToID")
    private User assignedTo;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}