package com.cts.entity;
import lombok.Data;

@Data
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
    public enum StatusCategory{
    	Active,
    	Inactive,
    	Transferred
    }
		private int userID;
		private String name;
		private RoleCategory role;
		private String email;
		private int phone;
		private int siteID;
		private int departmentID;
		
}
