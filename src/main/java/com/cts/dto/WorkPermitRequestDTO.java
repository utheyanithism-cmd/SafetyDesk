package com.cts.dto;

import java.time.LocalDateTime;

import com.cts.entity.WorkPermit.PermitType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkPermitRequestDTO {

	@NotNull(message = "Permit type is required")
    private PermitType permitType;

    @NotBlank(message = "IssuedToID is required")
    private String issuedToID;

    @NotBlank(message = "SiteID is required")
    private String siteID;

    @NotBlank(message = "Work location is required")
    private String workLocation;

    @NotBlank(message = "Work description is required")
    private String workDescription;

    @NotNull(message = "Start date-time is required")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date-time is required")
    private LocalDateTime endDateTime;

    private String hazardsIdentified;

    private String controlMeasures;
}
