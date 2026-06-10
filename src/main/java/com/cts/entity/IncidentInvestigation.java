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
public class IncidentInvestigation {

	public enum StatusCategory {
		InProgress,
		Completed,
		PendingApproval
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int investigationID;

	private int incidentID;
	private int investigatorID;
	private String rootCauses;
	private String contributingFactors;
	private String immediateActions;
	private String lessonsLearned;
	private LocalDate investigationDate;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}