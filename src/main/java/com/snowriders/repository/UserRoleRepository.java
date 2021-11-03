package com.snowriders.repository;

import com.snowriders.model.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {
}
