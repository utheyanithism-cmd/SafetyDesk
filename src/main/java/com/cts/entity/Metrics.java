package com.cts.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Metrics {

    private Integer totalIncidents;
    private Double ltifr;                          // Lost Time Injury Frequency Rate
    private Integer nearMissCount;
    private Double inspectionCompletionRate;
    private Double correctiveActionClosureRate;
    private Double permitComplianceRate;
    private Integer regulatoryObligationsOverdue;
}