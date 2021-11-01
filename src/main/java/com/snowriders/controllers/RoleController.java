package com.snowriders.controllers;

import com.snowriders.model.request.CreateRoleRequest;
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
    public ResponseEntity<CreateRoleRequest>saveRole(@RequestBody @Valid CreateRoleRequest role) {
        log.info("Start creating role with name {}", role.getRoleName());
        roleService.saveRole(role.getRoleName());
        return ResponseEntity.status(CREATED).body(role);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<Set<String>> getRoles() {
        log.info("Fetching all roles");
       return ResponseEntity.ok().body(roleService.getRoles());
    }

}
