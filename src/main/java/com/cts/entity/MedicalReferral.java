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
public class MedicalReferral {

	public enum StatusCategory {
		Referred,
		Attended,
		FollowUpRequired,
		Closed
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int referralID;

	private int healthRecordID;
	private int employeeID;
	private String referralReason;
	private String referredToSpeciality;
	private LocalDate referralDate;
	private String outcomeSummary;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}