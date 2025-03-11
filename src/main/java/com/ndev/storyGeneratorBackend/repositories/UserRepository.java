package com.ndev.storyGeneratorBackend.repositories;

import com.ndev.storyGeneratorBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);

    Optional<User> findByEmail(String email); // ✅ Return Optional<User>
    boolean existsByEmail(String email);
}
