package com.snowriders.model.request;

import lombok.Data;

@Data
public class UsernameAndPasswordRequest {
    private String username;
    private String password;
}
