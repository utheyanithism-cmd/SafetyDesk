package com.cts.dto.request;

import java.util.List;

import com.cts.enums.NotificationStatus;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BulkStatusUpdateRequest {

    @NotNull(message = "UserID is required")
    private Long userId;

    @NotEmpty(message = "At least one notificationId is required")
    private List<Long> notificationIds;

    @NotNull(message = "Status is required")
    private NotificationStatus status;
}