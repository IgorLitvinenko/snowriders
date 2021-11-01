package com.snowriders.service.impl;

import com.snowriders.entities.Role;
import com.snowriders.exceptions.DuplicateEntryException;
import com.snowriders.repositories.RoleRepository;
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

    @Override
    public Role saveRole(String roleName) {

        if (roleRepository.findByName(roleName).isPresent()) {
            log.error("Role {} is already exist", roleName);
            throw new DuplicateEntryException(String.format("Role %s is already exist", roleName));
        }
        //todo create mapper layer
        return roleRepository.save(new Role(roleName));
    }

    @Override
    public Set<String> getRoles() {
        return roleRepository.findAll().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
