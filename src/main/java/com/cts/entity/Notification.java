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
public class Notification {

	public enum Category {
		Incident,
		Permit,
		Inspection,
		Health,
		Compliance,
		CAPA
	}

	public enum StatusCategory {
		Unread,
		Read,
		Dismissed
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notificationID;

	private int userID;
	private String message;

	@Enumerated(EnumType.STRING)
	private Category category;

	@Enumerated(EnumType.STRING)
	private StatusCategory status;

	private LocalDateTime createdDate;
}