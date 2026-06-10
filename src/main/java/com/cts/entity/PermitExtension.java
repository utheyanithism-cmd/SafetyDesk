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
public class PermitExtension {

	public enum StatusCategory {
		Requested,
		Approved,
		Rejected
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int extensionID;

	private int permitID;
	private int requestedByID;
	private LocalDateTime newEndDateTime;
	private String reason;
	private int approvedByID;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;
}