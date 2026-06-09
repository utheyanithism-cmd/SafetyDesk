package com.cts.entity;
import java.time.LocalDate;

import lombok.Data;

@Data
public class HazardRecord {
	public enum HazardTypeCategory{
		Physical,
		Chemical,
		Biological,
		Ergonomic,
		Psychosocial
	}
	public enum StatusCategory{
		Open,
		Mitigated,
		Closed,
		Recurring
	}
	private int hazardID;
	private int siteID;
	private String location;
	private HazardTypeCategory hazardType;
	private String description;
	private int identifiedByID;
	private LocalDate identifiedDate;
	private StatusCategory status;
}
