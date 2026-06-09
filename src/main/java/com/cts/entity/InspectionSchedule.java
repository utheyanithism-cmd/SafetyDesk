package com.cts.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
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
