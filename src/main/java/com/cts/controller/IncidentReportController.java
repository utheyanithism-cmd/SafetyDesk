package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dto.IncidentReportRequest;
import com.cts.dto.IncidentReportResponse;
import com.cts.service.IncidentReportService;

@RestController
@RequestMapping("/api/incidents")
public class IncidentReportController {

	@Autowired
	private IncidentReportService incidentReportService;

	// CREATE -> POST /api/incidents
	@PostMapping
	public ResponseEntity<IncidentReportResponse> createIncident(@RequestBody IncidentReportRequest request) {
		IncidentReportResponse created = incidentReportService.createIncident(request);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	// READ ALL -> GET /api/incidents
	@GetMapping
	public ResponseEntity<List<IncidentReportResponse>> getAllIncidents() {
		List<IncidentReportResponse> incidents = incidentReportService.getAllIncidents();
		return ResponseEntity.ok(incidents);
	}

	// READ ONE -> GET /api/incidents/{id}
	@GetMapping("/{id}")
	public ResponseEntity<IncidentReportResponse> getIncidentById(@PathVariable int id) {
		IncidentReportResponse incident = incidentReportService.getIncidentById(id);
		return ResponseEntity.ok(incident);
	}

	// UPDATE -> PUT /api/incidents/{id}
	@PutMapping("/{id}")
	public ResponseEntity<IncidentReportResponse> updateIncident(@PathVariable int id,
			@RequestBody IncidentReportRequest request) {
		IncidentReportResponse updated = incidentReportService.updateIncident(id, request);
		return ResponseEntity.ok(updated);
	}

	// DELETE -> DELETE /api/incidents/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIncident(@PathVariable int id) {
		incidentReportService.deleteIncident(id);
		return ResponseEntity.noContent().build();
	}
}