package com.example.OnlineStationary.module.User.repository;

public class UserProfileRepository package com.example.OnlineStationary.user.repository;

import com.example.OnlineStationary.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {}{
}
