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
public class HealthRecord {

	public enum AssessmentType {
		PreEmployment,
		Periodic,
		PostIncident,
		ReturnToWork,
		Exit
	}

	public enum FitnessDecision {
		FitForWork,
		FitWithRestrictions,
		TemporaryUnfit,
		PermanentlyUnfit
	}

	public enum StatusCategory {
		Completed,
		PendingReview
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int healthRecordID;

	private int employeeID;

	@Enumerated(EnumType.STRING)
	private AssessmentType assessmentType;

	private LocalDate assessmentDate;
	private int conductedByID;

	@Enumerated(EnumType.STRING)
	private FitnessDecision fitnessDecision;

	private LocalDate nextAssessmentDate;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}