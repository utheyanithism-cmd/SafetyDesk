package com.cts.dto.request;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ExtensionDecisionRequest {

    @NotNull(message = "ApprovedByID is required")
    private Long approvedById;
}