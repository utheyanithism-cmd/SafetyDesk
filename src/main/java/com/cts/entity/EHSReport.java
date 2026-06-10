package com.cts.entity;

import java.time.LocalDate;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EHSReport {

	public enum ScopeCategory {
		Site,
		Department,
		Period
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reportID;

	@Enumerated(EnumType.STRING)
	private ScopeCategory scope;

	@Embedded
	private Metrics metrics;

	private LocalDate generatedDate;
}