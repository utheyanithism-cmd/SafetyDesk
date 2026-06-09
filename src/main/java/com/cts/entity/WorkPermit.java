package com.cts.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
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

    private int permitID;
    private PermitType permitType;
    private int issuedToID;
    private int siteID;
    private String workLocation;
    private String workDescription;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String hazardsIdentified;
    private String controlMeasures;
    private int approvedByID;
    private StatusCategory status;
}
