package com.cts.dto;

import java.time.LocalDateTime;

import com.cts.entity.WorkPermit.PermitType;
import com.cts.entity.WorkPermit.PermitStatus;

import lombok.Data;

@Data
public class WorkPermitResponseDTO {

	 private String permitID;
	    private PermitType permitType;
	    private String issuedToID;
	    private String siteID;
	    private String workLocation;
	    private String workDescription;
	    private LocalDateTime startDateTime;
	    private LocalDateTime endDateTime;
	    private String hazardsIdentified;
	    private String controlMeasures;
	    private String approvedByID;
	    private PermitStatus status;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
}
