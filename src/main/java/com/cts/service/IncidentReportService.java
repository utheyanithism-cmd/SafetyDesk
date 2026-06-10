package com.cts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dto.IncidentReportRequest;
import com.cts.dto.IncidentReportResponse;
import com.cts.entity.IncidentReport;
import com.cts.repository.IncidentReportRepository;

@Service
public class IncidentReportService {

	@Autowired
	private IncidentReportRepository incidentReportRepository;

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
				.orElseThrow(() -> new RuntimeException("Incident not found with id: " + id));
		return convertToResponse(incident);
	}

	// UPDATE
	public IncidentReportResponse updateIncident(int id, IncidentReportRequest request) {
		IncidentReport incident = incidentReportRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Incident not found with id: " + id));
		copyRequestToEntity(request, incident);
		IncidentReport updated = incidentReportRepository.save(incident);
		return convertToResponse(updated);
	}

	// DELETE
	public void deleteIncident(int id) {
		if (!incidentReportRepository.existsById(id)) {
			throw new RuntimeException("Incident not found with id: " + id);
		}
		incidentReportRepository.deleteById(id);
	}

	// ---------- helper methods ----------

	// copies fields from the incoming request DTO into an entity
	private void copyRequestToEntity(IncidentReportRequest request, IncidentReport incident) {
		incident.setReportedByID(request.getReportedByID());
		incident.setSiteID(request.getSiteID());
		incident.setIncidentDate(request.getIncidentDate());
		incident.setIncidentType(request.getIncidentType());
		incident.setDescription(request.getDescription());
		incident.setLocation(request.getLocation());
		incident.setInjuredPersonName(request.getInjuredPersonName());
		incident.setSeverity(request.getSeverity());
		incident.setAssignedInvestigatorID(request.getAssignedInvestigatorID());
		incident.setStatus(request.getStatus());
	}

	// copies fields from a saved entity into a response DTO
	private IncidentReportResponse convertToResponse(IncidentReport incident) {
		IncidentReportResponse response = new IncidentReportResponse();
		response.setIncidentID(incident.getIncidentID());
		response.setReportedByID(incident.getReportedByID());
		response.setSiteID(incident.getSiteID());
		response.setIncidentDate(incident.getIncidentDate());
		response.setIncidentType(incident.getIncidentType());
		response.setDescription(incident.getDescription());
		response.setLocation(incident.getLocation());
		response.setInjuredPersonName(incident.getInjuredPersonName());
		response.setSeverity(incident.getSeverity());
		response.setAssignedInvestigatorID(incident.getAssignedInvestigatorID());
		response.setStatus(incident.getStatus());
		return response;
	}
}