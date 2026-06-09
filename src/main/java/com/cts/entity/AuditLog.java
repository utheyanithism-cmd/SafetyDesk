package com.cts.entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditLog {

	
	private int auditID;
	private int userID;
	private String action;
	private String entityType;
	private int recordID;
	private LocalDateTime timestamp;
}
