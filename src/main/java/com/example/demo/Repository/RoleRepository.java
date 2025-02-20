package com.example.demo.Repository;

import com.example.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Query("SELECT r FROM Role r WHERE r.rolename = :rolename")
    Optional<Role> findByRolename(@Param("rolename") String rolename);
}
