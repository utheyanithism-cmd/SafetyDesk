package com.cts.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CorrectiveAction {

	public enum StatusCategory {
		Open,
		InProgress,
		Completed,
		Overdue,
		Verified
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int actionID;

	private int incidentID;
	private String description;
	private int assignedToID;
	private LocalDate dueDate;
	private LocalDate closedDate;
	private int verifiedByID;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}