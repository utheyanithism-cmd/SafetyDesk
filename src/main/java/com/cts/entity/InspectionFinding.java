package com.cts.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
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

    private int findingID;
    private int scheduleID;
    private FindingType findingType;
    private String description;
    private String location;
    private RiskLevel riskLevel;
    private int assignedToID;
    private LocalDate dueDate;
    private StatusCategory status;
}