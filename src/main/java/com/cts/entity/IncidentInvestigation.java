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
public class IncidentInvestigation {

    public enum StatusCategory {
        InProgress,
        Completed,
        PendingApproval
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer investigationID;

    // Many investigations → one incident report
    @ManyToOne
    @JoinColumn(name = "incidentID", nullable = false)
    private IncidentReport incident;

    // Many investigations → assigned to one investigator (SafetyOfficer)
    @ManyToOne
    @JoinColumn(name = "investigatorID", nullable = false)
    private User investigator;

    private String rootCauses;
    private String contributingFactors;
    private String immediateActions;
    private String lessonsLearned;
    private LocalDate investigationDate;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}