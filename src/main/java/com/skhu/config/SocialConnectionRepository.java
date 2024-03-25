package com.skhu.config;

import com.skhu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialConnectionRepository extends JpaRepository<SocialConnection, Long> {
    Optional<SocialConnection> findByUserAndSocialType(User user, SocialType socialType);
}