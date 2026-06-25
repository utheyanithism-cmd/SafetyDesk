package com.cts.service;

import java.util.List;

import com.cts.dto.request.UserRequest;
import com.cts.dto.request.UserUpdateRequest;
import com.cts.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse getUserById(Long userId);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long userId, UserUpdateRequest request);
    
    void deactivateUser(Long userId);
}