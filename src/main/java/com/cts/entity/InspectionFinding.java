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
public class InspectionFinding {

	public enum FindingType {
		NonConformance,
		Observation,
		BestPractice
	}

	public enum RiskLevel {
		Low,
		Medium,
		High,
		Critical
	}

	public enum StatusCategory {
		Open,
		InProgress,
		Closed,
		Overdue
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int findingID;

	private int scheduleID;

	@Enumerated(EnumType.STRING)
	private FindingType findingType;

	private String description;
	private String location;

	@Enumerated(EnumType.STRING)
	private RiskLevel riskLevel;

	private int assignedToID;
	private LocalDate dueDate;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}