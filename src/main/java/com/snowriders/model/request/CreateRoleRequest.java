package com.snowriders.model.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleRequest {

    @NotNull
    private String roleName;
}
