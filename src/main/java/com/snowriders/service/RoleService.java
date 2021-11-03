package com.snowriders.service;

import com.snowriders.model.request.CreateRoleRequest;
import com.snowriders.model.response.RoleResponse;

import java.util.Set;

public interface RoleService {

    RoleResponse createRole(CreateRoleRequest request);

    Set<String> getRoles();
}
