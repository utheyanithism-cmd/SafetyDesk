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
public class InspectionSchedule {

	public enum InspectionType {
		Routine,
		Compliance,
		Surprise,
		IncidentFollowUp
	}

	public enum StatusCategory {
		Scheduled,
		Completed,
		Missed,
		Rescheduled
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scheduleID;

	private int siteID;

	@Enumerated(EnumType.STRING)
	private InspectionType inspectionType;

	private int assignedOfficerID;
	private LocalDate plannedDate;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}