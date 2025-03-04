package com.ndev.storyGeneratorBackend.repositories;

import com.ndev.storyGeneratorBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // ✅ Return Optional<User>
    boolean existsByUsername(String username);
}
