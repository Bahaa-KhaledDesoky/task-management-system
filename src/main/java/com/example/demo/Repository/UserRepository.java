package com.example.demo.Repository;

import com.example.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<AppUser, Integer> {
    @Query("""
            SELECT u FROM AppUser u WHERE u.username = :username
            """)
    Optional<AppUser> findByUsername(String username);
}
