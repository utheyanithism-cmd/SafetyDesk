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
public class WorkPermit {

    public enum PermitType {
        HotWork,
        ConfinedSpace,
        ElectricalIsolation,
        WorkAtHeight,
        Excavation,
        ChemicalHandling
    }

    public enum StatusCategory {
        Draft,
        PendingApproval,
        Active,
        Suspended,
        Closed,
        Expired
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer permitID;

    @Enumerated(EnumType.STRING)
    private PermitType permitType;

    // Many permits → issued to one user (Employee / contractor)
    @ManyToOne
    @JoinColumn(name = "issuedToID", nullable = false)
    private User issuedTo;

    // siteID kept as plain FK until a Site entity is introduced
    private Integer siteID;

    private String workLocation;
    private String workDescription;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String hazardsIdentified;
    private String controlMeasures;

    // Many permits → approved by one user (PTWCoordinator / EHSManager)
    @ManyToOne
    @JoinColumn(name = "approvedByID")
    private User approvedBy;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}