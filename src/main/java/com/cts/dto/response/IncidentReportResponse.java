package com.cts.dto.response;

import java.time.LocalDateTime;

import com.cts.entity.IncidentReport.IncidentTypeCategory;
import com.cts.entity.IncidentReport.SeverityCategory;
import com.cts.entity.IncidentReport.StatusCategory;

import lombok.Data;

@Data
public class IncidentReportResponse {

	private int incidentID;
	private int reportedByID;
	private int siteID;
	private LocalDateTime incidentDate;
	private IncidentTypeCategory incidentType;
	private String description;
	private String location;
	private String injuredPersonName;
	private SeverityCategory severity;
	private int assignedInvestigatorID;
	private StatusCategory status;
}