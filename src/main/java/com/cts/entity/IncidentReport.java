package com.cts.entity;
import org.springframework.cglib.core.Local;

import jakarta.persistence.Entity;
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
	private int incidentID;
	private int reportedByID;
	private Local incidentDate;
	private IncidentTypeCategory incidentType;
	private String description;
	private String location;
	private String injuredPersonName;
	private SeverityCategory severity;
	private int assignedInvestigatorID;
	private StatusCategory status;
	
}
