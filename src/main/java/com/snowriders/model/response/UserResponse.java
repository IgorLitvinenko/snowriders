package com.snowriders.model.response;

import com.snowriders.entities.AppUser;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    //todo move to mapper
    public UserResponse(AppUser appUser) {
        this.id = appUser.getId();
        this.firstName = appUser.getFirstName();
        this.lastName = appUser.getLastName();
        this.email = appUser.getEmail();
    }
}
