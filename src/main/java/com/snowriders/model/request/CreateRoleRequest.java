package com.snowriders.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateRoleRequest {

    @NotNull
    private String roleName;
}
