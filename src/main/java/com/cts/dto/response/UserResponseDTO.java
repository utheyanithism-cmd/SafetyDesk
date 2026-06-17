package com.cts.dto.response;

import com.cts.entity.User.RoleCategory;
import com.cts.entity.User.StatusCategory;

import lombok.Data;

@Data
public class UserResponseDTO {
    private int userID;
    private String name;
    private RoleCategory role;
    private String email;
    private String phone;
    private int siteID;
    private int departmentID;
    private StatusCategory status;
}