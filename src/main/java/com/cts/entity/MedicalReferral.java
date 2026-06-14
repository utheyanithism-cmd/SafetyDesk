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
public class MedicalReferral {

    public enum StatusCategory {
        Referred,
        Attended,
        FollowUpRequired,
        Closed
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer referralID;

    // Many referrals → one health record (the assessment that triggered the referral)
    @ManyToOne
    @JoinColumn(name = "healthRecordID", nullable = false)
    private HealthRecord healthRecord;

    // Many referrals → one employee
    @ManyToOne
    @JoinColumn(name = "employeeID", nullable = false)
    private User employee;

    private String referralReason;
    private String referredToSpeciality;
    private LocalDate referralDate;
    private String outcomeSummary;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}