package com.cts.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MedicalReferral {

    public enum StatusCategory {
        Referred,
        Attended,
        FollowUpRequired,
        Closed
    }

    private int referralID;
    private int healthRecordID;
    private int employeeID;
    private String referralReason;
    private String referredToSpeciality;
    private LocalDate referralDate;
    private String outcomeSummary;
    private StatusCategory status;
}