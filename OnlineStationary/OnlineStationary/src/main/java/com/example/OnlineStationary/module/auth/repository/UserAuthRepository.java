package com.example.OnlineStationary.auth.repository;

import com.example.OnlineStationary.auth.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByUsername(String username);
    Optional<UserAuth> findByEmail(String email);
    Optional<UserAuth> findByUsernameOrEmail(String username, String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}