package com.snowriders.controllers;

import com.snowriders.entities.AppUser;
import com.snowriders.model.request.CreateRoleRequest;
import com.snowriders.model.response.UserResponse;
import com.snowriders.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<UserResponse>>getUsers() {
        log.info("Fetching all users");
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserResponse>getUser(@PathVariable Long id) {
        //todo logger
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //todo rename request body and add validations
    public ResponseEntity<UserResponse> createUser(@RequestBody AppUser appUser) {
        return ResponseEntity.status(CREATED).body(userService.saveUser(appUser));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @ResponseStatus(OK)
    public void addRoleToUser(@PathVariable Long id, @RequestBody CreateRoleRequest roleName) {
        userService.addRoleToUser(id, roleName.getRoleName());
    }
}

