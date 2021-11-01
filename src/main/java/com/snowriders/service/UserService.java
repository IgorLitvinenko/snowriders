package com.snowriders.service;

import com.snowriders.entities.AppUser;
import com.snowriders.model.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse saveUser(AppUser appUser);

    void addRoleToUser(Long id, String roleName);

    UserResponse getUser(Long id);

    List<UserResponse> getUsers();

}
