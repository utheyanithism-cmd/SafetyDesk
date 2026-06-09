package com.cts.entity;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
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
