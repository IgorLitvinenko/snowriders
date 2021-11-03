package com.snowriders.mapper;

import com.snowriders.model.entities.AppUser;
import com.snowriders.model.entities.Role;
import com.snowriders.model.entities.UserRoles;
import com.snowriders.model.request.UserRequest;
import com.snowriders.model.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(AppUser user) {
       return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public AppUser toUser(UserRequest request) {
        return AppUser.builder()
                .email(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    public UserRoles createUserRole(AppUser user, Role role) {
        return UserRoles.builder()
                .user(user)
                .role(role)
                .build();
    }
}
