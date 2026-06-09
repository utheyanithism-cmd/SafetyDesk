package com.cts.entity;
import java.time.LocalDate;

import lombok.Data;

@Data
public class CorrectiveAction {
	
	public enum StatusCategory{
		Open,
		InProgress,
		Completed,
		Overdue,
		Verified
	}
	private int actionID;
	private int incidentID;
	private String description;
	private int assignedToID;
	private LocalDate dueDate;
	private LocalDate closedDate;
	private int verifiedByID;
	private StatusCategory status;
}
