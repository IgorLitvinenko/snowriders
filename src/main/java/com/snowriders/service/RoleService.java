package com.snowriders.service;

import com.snowriders.entities.Role;

import java.util.Set;

public interface RoleService {

    Role saveRole(String roleName);

    Set<String> getRoles();
}
