package com.cts.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.AuditLogResponseDTO;
import com.cts.service.AuditLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
// Only EHSManager, ComplianceOfficer, and Admin may access any endpoint in this controller
@PreAuthorize("hasAnyRole('EHSManager', 'ComplianceOfficer', 'Admin')")
public class AuditLogController {

    private final AuditLogService auditLogService;

    // ── GET /api/audit-logs ──────────────────────────────────────────────────────
    // Paginated + filtered audit log listing
    // Default: page=0, size=20, sorted by timestamp DESC
    //
    // Query params (all optional):
    //   userID, entityType, recordID, action, from, to, page, size, sortBy, sortDir
    //
    // Returns: 200 OK with Page<AuditLogResponse>
    //          400 Bad Request if date format is invalid (Spring handles this automatically)
    //          403 Forbidden if caller lacks required role
    @GetMapping
    public ResponseEntity<Page<AuditLogResponseDTO>> getAuditLogs(
            @RequestParam(required = false) Integer userID,
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) Integer recordID,
            @RequestParam(required = false) String action,
            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "0")    int    page,
            @RequestParam(defaultValue = "20")   int    size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Page<AuditLogResponseDTO> result = auditLogService.getAuditLogs(
                userID, entityType, recordID, action, from, to,
                page, size, sortBy, sortDir);
        return ResponseEntity.ok(result);
    }

    // ── GET /api/audit-logs/{id} ─────────────────────────────────────────────────
    // Fetch a single audit log entry by its ID
    //
    // Returns: 200 OK with AuditLogResponse
    //          404 Not Found (thrown by service → handled by GlobalExceptionHandler)
    //          403 Forbidden if caller lacks required role
    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponseDTO> getAuditLogById(@PathVariable Integer id) {
        return ResponseEntity.ok(auditLogService.getAuditLogById(id));
    }

    // ── GET /api/audit-logs/export/csv ───────────────────────────────────────────
    // Exports all matching audit log entries as a downloadable CSV file.
    // Accepts the same filter params as the paginated endpoint (no pagination params).
    //
    // Returns: 200 OK with CSV file attachment
    //          403 Forbidden if caller lacks required role
    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportCsv(
            @RequestParam(required = false) Integer userID,
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) Integer recordID,
            @RequestParam(required = false) String action,
            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        byte[] csv = auditLogService.exportToCsv(userID, entityType, recordID, action, from, to);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "audit-log.csv");

        return new ResponseEntity<>(csv, headers, HttpStatus.OK);
    }

    // ── GET /api/audit-logs/export/pdf ───────────────────────────────────────────
    // Exports all matching audit log entries as a downloadable PDF file.
    // Accepts the same filter params as the paginated endpoint (no pagination params).
    //
    // Returns: 200 OK with PDF file attachment
    //          403 Forbidden if caller lacks required role
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdf(
            @RequestParam(required = false) Integer userID,
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) Integer recordID,
            @RequestParam(required = false) String action,
            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        byte[] pdf = auditLogService.exportToPdf(userID, entityType, recordID, action, from, to);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "audit-log.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}