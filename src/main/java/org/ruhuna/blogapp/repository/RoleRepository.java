package org.ruhuna.blogapp.repository;

import org.ruhuna.blogapp.model.AppRole;
import org.ruhuna.blogapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
