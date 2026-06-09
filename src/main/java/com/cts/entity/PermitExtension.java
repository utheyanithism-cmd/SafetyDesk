package com.cts.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PermitExtension {

    public enum StatusCategory {
        Requested,
        Approved,
        Rejected
    }

    private int extensionID;
    private int permitID;
    private int requestedByID;
    private LocalDateTime newEndDateTime;
    private String reason;
    private int approvedByID;
    private StatusCategory status;
}