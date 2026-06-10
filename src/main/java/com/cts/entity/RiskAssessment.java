package com.cts.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
public class RiskAssessment {

	public enum StatusCategory {
		Draft,
		Approved,
		Superseded
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int assessmentID;

	private int hazardID;
	private String taskDescription;

	@Min(1)
	@Max(5)
	private int likelihood;

	@Min(1)
	@Max(5)
	private int severity;

	private int riskRating;
	private String existingControls;
	private String additionalControls;
	private String residualRisk;
	private int assessedByID;
	private LocalDate assessmentDate;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}