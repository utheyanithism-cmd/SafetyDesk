package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.entity.IncidentReport;

@Repository
public interface IncidentReportRepository extends JpaRepository<IncidentReport, Integer> {

}