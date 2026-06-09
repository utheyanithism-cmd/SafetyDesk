package com.cts.entity;

import lombok.Data;
import java.time.LocalDate;

import jakarta.persistence.Entity;

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

    private int healthRecordID;
    private int employeeID;
    private AssessmentType assessmentType;
    private LocalDate assessmentDate;
    private int conductedByID;
    private FitnessDecision fitnessDecision;
    private LocalDate nextAssessmentDate;
    private StatusCategory status;
}