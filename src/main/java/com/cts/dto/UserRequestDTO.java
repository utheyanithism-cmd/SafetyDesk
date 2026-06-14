package com.cts.dto;

import com.cts.entity.User.RoleCategory;
import com.cts.entity.User.StatusCategory;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Role is required")
    private RoleCategory role;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    private String phone;

    @NotNull(message = "SiteID is required")
    private int siteID;

    private int departmentID;

    private StatusCategory status;

    private String password;
}