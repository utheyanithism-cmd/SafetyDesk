package com.cts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    public enum RoleCategory {
        Employee, SafetyOfficer, PTWCoordinator,
        OHNurse, EHSManager, ComplianceOfficer, Admin
    }

    public enum StatusCategory {
        Active, Inactive, Transferred
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleCategory role;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @Column(nullable = false)
    private Integer siteID;

    @Column(nullable = false)
    private Integer departmentID;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;

    private String password;
}