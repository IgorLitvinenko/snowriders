package com.snowriders.service;

import com.snowriders.model.request.UserRequest;
import com.snowriders.model.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    void addRoleToUser(Long userId, String roleName);

    UserResponse getUser(Long id);

    List<UserResponse> getUsers();

}
