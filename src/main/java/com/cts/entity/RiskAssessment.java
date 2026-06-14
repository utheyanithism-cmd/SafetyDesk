package com.cts.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
public class RiskAssessment {

    public enum StatusCategory {
        Draft,
        Approved,
        Superseded
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer assessmentID;

    // Many risk assessments → one hazard record
    @ManyToOne
    @JoinColumn(name = "hazardID", nullable = false)
    private HazardRecord hazard;

    private String taskDescription;

    @Min(1)
    @Max(5)
    private int likelihood;

    @Min(1)
    @Max(5)
    private int severity;

    private int riskRating;
    private String existingControls;
    private String additionalControls;
    private String residualRisk;

    // Many risk assessments → assessed by one user (SafetyOfficer / EHSManager)
    @ManyToOne
    @JoinColumn(name = "assessedByID", nullable = false)
    private User assessedBy;

    private LocalDate assessmentDate;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}