package com.cts.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AuditLogResponseDTO {

    private Integer auditID;
    private Integer userID;       // flat integer — avoids nested User serialization
    private String  userName;     // included for display convenience in UI/reports
    private String  action;
    private String  entityType;
    private Integer recordID;
    private LocalDateTime timestamp;
}