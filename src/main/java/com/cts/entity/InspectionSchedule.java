package com.cts.entity;

import lombok.Data;
import java.time.LocalDate;

import jakarta.persistence.Entity;

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

    private int scheduleID;
    private int siteID;
    private InspectionType inspectionType;
    private int assignedOfficerID;
    private LocalDate plannedDate;
    private StatusCategory status;
}
