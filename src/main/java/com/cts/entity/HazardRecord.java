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
public class HazardRecord {

	public enum HazardTypeCategory {
		Physical,
		Chemical,
		Biological,
		Ergonomic,
		Psychosocial
	}

	public enum StatusCategory {
		Open,
		Mitigated,
		Closed,
		Recurring
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int hazardID;

	private int siteID;
	private String location;

	@Enumerated(EnumType.STRING)
	private HazardTypeCategory hazardType;

	private String description;
	private int identifiedByID;
	private LocalDate identifiedDate;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}