package com.cts.entity;

import jakarta.persistence.*;
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
    private int userID;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleCategory role;

    @Column(unique = true, nullable = false)  // enforces unique email
    private String email;

    private String phone;

    @Column(nullable = false)
    private int siteID;

    @Column(nullable = false)
    private int departmentID;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;

    private String password;
}