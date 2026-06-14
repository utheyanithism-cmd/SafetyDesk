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
public class HealthRecord {

    public enum AssessmentType {
        PreEmployment,
        Periodic,
        PostIncident,
        ReturnToWork,
        Exit
    }

    public enum FitnessDecision {
        FitForWork,
        FitWithRestrictions,
        TemporaryUnfit,
        PermanentlyUnfit
    }

    public enum StatusCategory {
        Completed,
        PendingReview
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer healthRecordID;

    // Many health records → one employee
    @ManyToOne
    @JoinColumn(name = "employeeID", nullable = false)
    private User employee;

    @Enumerated(EnumType.STRING)
    private AssessmentType assessmentType;

    private LocalDate assessmentDate;

    // Many health records → conducted by one nurse / health officer (OHNurse)
    @ManyToOne
    @JoinColumn(name = "conductedByID", nullable = false)
    private User conductedBy;

    @Enumerated(EnumType.STRING)
    private FitnessDecision fitnessDecision;

    private LocalDate nextAssessmentDate;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}