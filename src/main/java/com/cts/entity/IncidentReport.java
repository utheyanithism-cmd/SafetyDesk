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
public class IncidentReport {

    public enum IncidentTypeCategory {
        Injury,
        NearMiss,
        PropertyDamage,
        EnvironmentalRelease,
        UnsafeAct,
        UnsafeCondition
    }

    public enum SeverityCategory {
        Minor,
        Moderate,
        Serious,
        Fatal
    }

    public enum StatusCategory {
        Reported,
        UnderInvestigation,
        CAPAAssigned,
        Closed
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incidentID;

    // Many incidents → reported by one user (Employee)
    @ManyToOne
    @JoinColumn(name = "reportedByID", nullable = false)
    private User reportedBy;

    // siteID is not a User; kept as plain FK until a Site entity exists
    private Integer siteID;

    private LocalDateTime incidentDate;

    @Enumerated(EnumType.STRING)
    private IncidentTypeCategory incidentType;

    private String description;
    private String location;
    private String injuredPersonName;

    @Enumerated(EnumType.STRING)
    private SeverityCategory severity;

    // Many incidents → assigned to one investigator (SafetyOfficer)
    @ManyToOne
    @JoinColumn(name = "assignedInvestigatorID")
    private User assignedInvestigator;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}