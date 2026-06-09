package com.cts.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    public enum RoleCategory {
        Employee,
        SafetyOfficer,
        PTWCoordinator,
        OHNurse,
        EHSManager,
        ComplianceOfficer,
        Admin
    }

    public enum StatusCategory {
        Active,
        Inactive,
        Transferred
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    private String name;

    @Enumerated(EnumType.STRING)
    private RoleCategory role;

    private String email;
    private String phone;      // fix 1: String not int

    private int siteID;
    private int departmentID;

    @Enumerated(EnumType.STRING)
    private StatusCategory status;  // fix 2: was missing

    private String password;   // needed for Login API
}