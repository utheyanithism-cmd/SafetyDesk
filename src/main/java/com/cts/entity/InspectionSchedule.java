package com.cts.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class InspectionSchedule {

    public enum InspectionType {
        Routine,
        Compliance,
        Surprise,
        IncidentFollowUp
    }

    public enum StatusCategory {
        Scheduled,
        Completed,
        Missed,
        Rescheduled
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scheduleID;

    // siteID kept as plain FK until a Site entity is introduced
    private Integer siteID;

    @Enumerated(EnumType.STRING)
    private InspectionType inspectionType;

    // Many inspection schedules → assigned to one officer (SafetyOfficer / ComplianceOfficer)
    @ManyToOne
    @JoinColumn(name = "assignedOfficerID", nullable = false)
    private User assignedOfficer;

    private LocalDate plannedDate;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}