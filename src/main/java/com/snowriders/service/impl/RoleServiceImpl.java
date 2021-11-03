package com.snowriders.service.impl;

import com.snowriders.mapper.RoleMapper;
import com.snowriders.model.entities.Role;
import com.snowriders.exception.DuplicateEntryException;
import com.snowriders.model.request.CreateRoleRequest;
import com.snowriders.model.response.RoleResponse;
import com.snowriders.repository.RoleRepository;
import com.snowriders.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper mapper;

    @Override
    public RoleResponse createRole(CreateRoleRequest request) {

        String roleName = request.getRoleName();

        if (roleRepository.findByName(roleName).isPresent()) {
            log.error("Role {} is already exist", roleName);
            throw new DuplicateEntryException(String.format("Role %s is already exist", roleName));
        }
        //todo create mapper layer

        Role role = mapper.toRole(request);
        roleRepository.save(mapper.toRole(request));
        return mapper.toResponse(role);
    }

    @Override
    public Set<String> getRoles() {
        return roleRepository.findAll().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
