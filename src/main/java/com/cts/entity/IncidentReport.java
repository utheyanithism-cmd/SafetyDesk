package com.cts.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class IncidentReport {

	public enum IncidentTypeCategory {
		Injury,
		NearMiss,
		PropertyDamage,
		EnvironmentalRelease,
		UnsafeAct,
		UnsafeCondition
	}

	public enum SeverityCategory {
		Minor,
		Moderate,
		Serious,
		Fatal
	}

	public enum StatusCategory {
		Reported,
		UnderInvestigation,
		CAPAAssigned,
		Closed
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int incidentID;

	private int reportedByID;

	private int siteID;

	private LocalDateTime incidentDate;

	@Enumerated(EnumType.STRING)
	private IncidentTypeCategory incidentType;

	private String description;
	private String location;
	private String injuredPersonName;

	@Enumerated(EnumType.STRING)
	private SeverityCategory severity;

	private int assignedInvestigatorID;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}