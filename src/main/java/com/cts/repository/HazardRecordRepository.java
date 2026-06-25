package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cts.entity.HazardRecord;

@Repository
public interface HazardRecordRepository
        extends JpaRepository<HazardRecord, Long>, JpaSpecificationExecutor<HazardRecord> {
}