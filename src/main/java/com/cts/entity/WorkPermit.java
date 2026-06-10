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
public class WorkPermit {

	public enum PermitType {
		HotWork,
		ConfinedSpace,
		ElectricalIsolation,
		WorkAtHeight,
		Excavation,
		ChemicalHandling
	}

	public enum StatusCategory {
		Draft,
		PendingApproval,
		Active,
		Suspended,
		Closed,
		Expired
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int permitID;

	@Enumerated(EnumType.STRING)
	private PermitType permitType;

	private int issuedToID;
	private int siteID;
	private String workLocation;
	private String workDescription;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;
	private String hazardsIdentified;
	private String controlMeasures;
	private int approvedByID;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}