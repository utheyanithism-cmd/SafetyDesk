package com.cts.dto;

import java.time.LocalDateTime;

import com.cts.entity.PermitExtension.ExtensionStatus;

import lombok.Data;

@Data
public class PermitExtensionResponseDTO {

	  private String extensionID;
	    private String permitID;
	    private String requestedByID;
	    private LocalDateTime newEndDateTime;
	    private String reason;
	    private String approvedByID;
	    private ExtensionStatus status;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
}
