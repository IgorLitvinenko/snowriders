package com.snowriders.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
@Builder
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String telephone;
    @Builder.Default
    private Boolean enable = true;
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private Collection<UserRoles> roles = new HashSet<>();

    //todo create another class with user details
}
