// src/main/java/com/mytickets/ticketingApp/repository/RoleRepository.java
package com.mytickets.ticketingApp.repository;

import com.mytickets.ticketingApp.model.ERole;
import com.mytickets.ticketingApp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    boolean existsByName(ERole role);
}