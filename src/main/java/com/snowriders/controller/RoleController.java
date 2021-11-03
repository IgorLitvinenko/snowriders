package com.snowriders.controller;

import com.snowriders.model.request.CreateRoleRequest;
import com.snowriders.model.response.RoleResponse;
import com.snowriders.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/roles")
public class RoleController {

   private final RoleService roleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")

    //todo should return role response
    public ResponseEntity<RoleResponse>createRole(@RequestBody @Valid CreateRoleRequest role) {
        log.info("Creating role with name {}", role.getRoleName());
        return ResponseEntity.status(CREATED).body(roleService.createRole(role));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Set<String>> getRoles() {
        log.info("Fetching all roles");
       return ResponseEntity.ok().body(roleService.getRoles());
    }

}
