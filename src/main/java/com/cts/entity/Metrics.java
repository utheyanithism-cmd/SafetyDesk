package com.cts.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Metrics {

	private int totalIncidents;
	private double ltifr; // Lost Time Injury Frequency Rate
	private int nearMissCount;
	private double inspectionCompletionRate;
	private double correctiveActionClosureRate;
	private double permitComplianceRate;
	private int regulatoryObligationsOverdue;
}