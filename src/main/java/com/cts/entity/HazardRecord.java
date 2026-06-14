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
public class HazardRecord {

    public enum HazardTypeCategory {
        Physical,
        Chemical,
        Biological,
        Ergonomic,
        Psychosocial
    }

    public enum StatusCategory {
        Open,
        Mitigated,
        Closed,
        Recurring
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hazardID;

    // siteID kept as plain FK until a Site entity is introduced
    private Integer siteID;

    private String location;

    @Enumerated(EnumType.STRING)
    private HazardTypeCategory hazardType;

    private String description;

    // Many hazard records → identified by one user (SafetyOfficer / Employee)
    @ManyToOne
    @JoinColumn(name = "identifiedByID", nullable = false)
    private User identifiedBy;

    private LocalDate identifiedDate;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}