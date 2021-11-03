package com.snowriders.mapper;

import com.snowriders.model.entities.Role;
import com.snowriders.model.request.CreateRoleRequest;
import com.snowriders.model.response.RoleResponse;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .roleName(role.getName())
                .build();
    }

    public Role toRole(CreateRoleRequest request) {
        return Role.builder()
                .name(request.getRoleName())
                .build();
    }
}
