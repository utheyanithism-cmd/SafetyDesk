package com.cts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cts.dto.IncidentReportRequest;
import com.cts.dto.IncidentReportResponse;
import com.cts.entity.IncidentReport;
import com.cts.entity.User;
import com.cts.exception.IncidentNotFoundException;
import com.cts.exception.UserNotFoundException;
import com.cts.repository.IncidentReportRepository;
import com.cts.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentReportService {

    private final IncidentReportRepository incidentReportRepository;

    // Needed to resolve reportedBy and assignedInvestigator from IDs in the request DTO
    private final UserRepository userRepository;

    // CREATE
    public IncidentReportResponse createIncident(IncidentReportRequest request) {
        IncidentReport incident = new IncidentReport();
        copyRequestToEntity(request, incident);
        IncidentReport saved = incidentReportRepository.save(incident);
        return convertToResponse(saved);
    }

    // READ ALL
    public List<IncidentReportResponse> getAllIncidents() {
        return incidentReportRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // READ ONE
    public IncidentReportResponse getIncidentById(int id) {
        IncidentReport incident = incidentReportRepository.findById(id)
                .orElseThrow(() -> new IncidentNotFoundException("Incident not found with id: " + id));
        return convertToResponse(incident);
    }

    // UPDATE
    public IncidentReportResponse updateIncident(int id, IncidentReportRequest request) {
        IncidentReport incident = incidentReportRepository.findById(id)
                .orElseThrow(() -> new IncidentNotFoundException("Incident not found with id: " + id));
        copyRequestToEntity(request, incident);
        IncidentReport updated = incidentReportRepository.save(incident);
        return convertToResponse(updated);
    }

    // DELETE
    public void deleteIncident(int id) {
        if (!incidentReportRepository.existsById(id)) {
            throw new IncidentNotFoundException("Incident not found with id: " + id);
        }
        incidentReportRepository.deleteById(id);
    }

    // ---------- helper methods ----------

    /*
     * The request DTO still carries plain integer IDs (reportedByID, assignedInvestigatorID).
     * We look up the User entities here so the entity layer only ever holds object references,
     * not raw FK integers.
     */
    private void copyRequestToEntity(IncidentReportRequest request, IncidentReport incident) {

        // Resolve reportedBy: mandatory — every incident must have a reporter
        User reportedBy = userRepository.findById(request.getReportedByID())
                .orElseThrow(() -> new UserNotFoundException(
                        "Reporting user not found with id: " + request.getReportedByID()));
        incident.setReportedBy(reportedBy);

        incident.setSiteID(request.getSiteID());
        incident.setIncidentDate(request.getIncidentDate());
        incident.setIncidentType(request.getIncidentType());
        incident.setDescription(request.getDescription());
        incident.setLocation(request.getLocation());
        incident.setInjuredPersonName(request.getInjuredPersonName());
        incident.setSeverity(request.getSeverity());

        // Resolve assignedInvestigator: optional at creation time (may be assigned later)
        if (request.getAssignedInvestigatorID() != null) {
            User investigator = userRepository.findById(request.getAssignedInvestigatorID())
                    .orElseThrow(() -> new UserNotFoundException(
                            "Investigator not found with id: " + request.getAssignedInvestigatorID()));
            incident.setAssignedInvestigator(investigator);
        }

        incident.setStatus(request.getStatus());
    }

    /*
     * The response DTO still exposes plain integer IDs so API consumers don't receive
     * full nested User objects (which would cause infinite recursion with @ManyToOne).
     * We extract the IDs from the resolved User references here.
     */
    private IncidentReportResponse convertToResponse(IncidentReport incident) {
        IncidentReportResponse response = new IncidentReportResponse();
        response.setIncidentID(incident.getIncidentID());

        // Extract the integer ID from the User object
        response.setReportedByID(incident.getReportedBy().getUserID());

        response.setSiteID(incident.getSiteID());
        response.setIncidentDate(incident.getIncidentDate());
        response.setIncidentType(incident.getIncidentType());
        response.setDescription(incident.getDescription());
        response.setLocation(incident.getLocation());
        response.setInjuredPersonName(incident.getInjuredPersonName());
        response.setSeverity(incident.getSeverity());

        // assignedInvestigator is nullable
        if (incident.getAssignedInvestigator() != null) {
            response.setAssignedInvestigatorID(incident.getAssignedInvestigator().getUserID());
        }

        response.setStatus(incident.getStatus());
        return response;
    }
}