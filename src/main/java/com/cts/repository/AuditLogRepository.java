package com.cts.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.entity.AuditLog;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    // Filter by user
    Page<AuditLog> findByUser_UserID(Integer userID, Pageable pageable);

    // Filter by entity type (e.g. "IncidentReport", "WorkPermit")
    Page<AuditLog> findByEntityType(String entityType, Pageable pageable);

    // Filter by specific record within an entity type
    Page<AuditLog> findByEntityTypeAndRecordID(String entityType, Integer recordID, Pageable pageable);

    // Filter by action keyword (e.g. "USER_REGISTERED", "PERMIT_APPROVED")
    Page<AuditLog> findByAction(String action, Pageable pageable);

    // Filter by date range
    Page<AuditLog> findByTimestampBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    /*
     * Combined filter — all parameters are optional.
     * JPQL uses COALESCE so passing null for any param means "ignore that filter".
     */
    @Query("""
            SELECT a FROM AuditLog a
            WHERE (:userID     IS NULL OR a.user.userID  = :userID)
              AND (:entityType IS NULL OR a.entityType   = :entityType)
              AND (:recordID   IS NULL OR a.recordID     = :recordID)
              AND (:action     IS NULL OR a.action       = :action)
              AND (:from       IS NULL OR a.timestamp   >= :from)
              AND (:to         IS NULL OR a.timestamp   <= :to)
            """)
    Page<AuditLog> findByFilters(
            @Param("userID")     Integer userID,
            @Param("entityType") String entityType,
            @Param("recordID")   Integer recordID,
            @Param("action")     String action,
            @Param("from")       LocalDateTime from,
            @Param("to")         LocalDateTime to,
            Pageable pageable
    );

    /*
     * Same combined filter without pagination — used by the CSV/PDF export methods
     * so the export fetches all matching rows, not just one page.
     */
    @Query("""
            SELECT a FROM AuditLog a
            WHERE (:userID     IS NULL OR a.user.userID  = :userID)
              AND (:entityType IS NULL OR a.entityType   = :entityType)
              AND (:recordID   IS NULL OR a.recordID     = :recordID)
              AND (:action     IS NULL OR a.action       = :action)
              AND (:from       IS NULL OR a.timestamp   >= :from)
              AND (:to         IS NULL OR a.timestamp   <= :to)
            ORDER BY a.timestamp DESC
            """)
    java.util.List<AuditLog> findAllByFiltersForExport(
            @Param("userID")     Integer userID,
            @Param("entityType") String entityType,
            @Param("recordID")   Integer recordID,
            @Param("action")     String action,
            @Param("from")       LocalDateTime from,
            @Param("to")         LocalDateTime to
    );
}