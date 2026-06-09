package com.cts.entity;
import lombok.Data;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Data
@Entity
public class RiskAssessment {
	public enum StatusCategory{
		Draft,
		Approved,
		Superseded
	}
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
	private int assessmentByID;
	private LocalDate assessmentDate;
	private StatusCategory status;
}
