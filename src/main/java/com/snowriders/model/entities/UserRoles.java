package com.snowriders.model.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_roles")
@Builder
@AllArgsConstructor
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public String toString() {
        return "UserRoles{" +
                "id=" + id +
//                ", user=" + user +
                ", role=" + role +
                '}';
    }
}
