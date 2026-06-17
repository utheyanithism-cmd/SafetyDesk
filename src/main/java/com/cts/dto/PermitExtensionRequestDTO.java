package com.cts.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermitExtensionRequestDTO {

	@NotBlank(message = "PermitID is required")
    private String permitID;

    @NotBlank(message = "RequestedByID is required")
    private String requestedByID;

    @NotNull(message = "NewEndDateTime is required")
    private LocalDateTime newEndDateTime;

    @NotBlank(message = "Reason is required")
    private String reason;
}
