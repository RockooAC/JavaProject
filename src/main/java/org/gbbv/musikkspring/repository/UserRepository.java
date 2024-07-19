package org.gbbv.musikkspring.repository;

import org.gbbv.musikkspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    boolean existsByEmail(String mail);
}