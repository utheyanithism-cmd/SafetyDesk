package com.cts.dto;

import com.cts.entity.InspectionSchedule.InspectionType;
import com.cts.entity.InspectionSchedule.StatusCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionScheduleResponse {
	private int scheduleID;
    private int siteID;
    private InspectionType inspectionType;
    private int assignedOfficerID;
    private LocalDate plannedDate;
    private StatusCategory status;
}
