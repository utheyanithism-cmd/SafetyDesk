package com.cts.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cts.dto.AuditLogResponseDTO;
import com.cts.entity.AuditLog;
import com.cts.exception.AuditLogNotFoundException;
import com.cts.repository.AuditLogRepository;

// iText5 imports — all from com.itextpdf.text (NOT java.awt)
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // ── READ: paginated + filtered ──────────────────────────────────────────────

    public Page<AuditLogResponseDTO> getAuditLogs(
            Integer userID,
            String entityType,
            Integer recordID,
            String action,
            LocalDateTime from,
            LocalDateTime to,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return auditLogRepository
                .findByFilters(userID, entityType, recordID, action, from, to, pageable)
                .map(this::toResponse);
    }

    // ── READ: single entry ──────────────────────────────────────────────────────

    public AuditLogResponseDTO getAuditLogById(Integer id) {
        AuditLog log = auditLogRepository.findById(id)
                .orElseThrow(() -> new AuditLogNotFoundException(
                        "Audit log not found with id: " + id));
        return toResponse(log);
    }

    // ── EXPORT: CSV ─────────────────────────────────────────────────────────────

    public byte[] exportToCsv(
            Integer userID,
            String entityType,
            Integer recordID,
            String action,
            LocalDateTime from,
            LocalDateTime to
    ) {
        List<AuditLog> logs = auditLogRepository
                .findAllByFiltersForExport(userID, entityType, recordID, action, from, to);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        writer.println("AuditID,UserID,UserName,Action,EntityType,RecordID,Timestamp");

        for (AuditLog log : logs) {
            writer.printf("%d,%d,%s,%s,%s,%d,%s%n",
                    log.getAuditID(),
                    log.getUser().getUserID(),
                    escapeCsv(log.getUser().getName()),
                    escapeCsv(log.getAction()),
                    escapeCsv(log.getEntityType()),
                    log.getRecordID(),
                    log.getTimestamp().format(FORMATTER)
            );
        }

        writer.flush();
        return out.toByteArray();
    }

    // ── EXPORT: PDF (iText5) ─────────────────────────────────────────────────────

    public byte[] exportToPdf(
            Integer userID,
            String entityType,
            Integer recordID,
            String action,
            LocalDateTime from,
            LocalDateTime to
    ) {
        List<AuditLog> logs = auditLogRepository
                .findAllByFiltersForExport(userID, entityType, recordID, action, from, to);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // com.itextpdf.text.Document — NOT java.awt anything
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, out);
            document.open();

            // com.itextpdf.text.Font — NOT java.awt.Font
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            document.add(new Paragraph("SafetyDesk - Audit Log Report", titleFont));

            Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            document.add(new Paragraph(
                    "Generated: " + LocalDateTime.now().format(FORMATTER), subFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 1f, 2f, 2f, 2f, 1f, 2f});

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
            for (String header : new String[]{
                    "AuditID", "UserID", "UserName", "Action",
                    "EntityType", "RecordID", "Timestamp"
            }) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setPadding(5);
                table.addCell(cell);
            }

            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
            for (AuditLog log : logs) {
                table.addCell(new Phrase(String.valueOf(log.getAuditID()), dataFont));
                table.addCell(new Phrase(String.valueOf(log.getUser().getUserID()), dataFont));
                table.addCell(new Phrase(log.getUser().getName(), dataFont));
                table.addCell(new Phrase(log.getAction(), dataFont));
                table.addCell(new Phrase(log.getEntityType(), dataFont));
                table.addCell(new Phrase(String.valueOf(log.getRecordID()), dataFont));
                table.addCell(new Phrase(log.getTimestamp().format(FORMATTER), dataFont));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF: " + e.getMessage(), e);
        }

        return out.toByteArray();
    }

    // ── helpers ─────────────────────────────────────────────────────────────────

    private AuditLogResponseDTO toResponse(AuditLog log) {
        AuditLogResponseDTO response = new AuditLogResponseDTO();
        response.setAuditID(log.getAuditID());
        response.setUserID(log.getUser().getUserID());
        response.setUserName(log.getUser().getName());
        response.setAction(log.getAction());
        response.setEntityType(log.getEntityType());
        response.setRecordID(log.getRecordID());
        response.setTimestamp(log.getTimestamp());
        return response;
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}