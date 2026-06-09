package com.cts.entity;
import lombok.Data;
import java.time.LocalDate;

import jakarta.persistence.Entity;

@Data
@Entity
public class IncidentInvestigation {
	
		public enum StatusCategory{
			InProgress,
			Completed,
			Pending,
			Apporval
		}
		private int investigationID;
		private int incidentID;
		private int investigatorID;
		private String rootCauses;
		private String contributingFactors;
		private String immediateActions;
		private String lessonsLearned;
		private LocalDate investigationDate;
		private StatusCategory status;
		
}
