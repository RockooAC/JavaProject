package org.gbbv.musikkspring.repository;


import org.gbbv.musikkspring.model.Role;
import org.gbbv.musikkspring.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    boolean existsByName(String roleName);


}