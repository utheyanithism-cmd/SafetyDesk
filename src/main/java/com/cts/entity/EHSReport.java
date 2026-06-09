package com.cts.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EHSReport {

    public enum ScopeCategory {
        Site,
        Department,
        Period
    }

    private int reportID;
    private ScopeCategory scope;
    private Metrics metrics;
    private LocalDate generatedDate;
}
