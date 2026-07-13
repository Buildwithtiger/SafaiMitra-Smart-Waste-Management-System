package com.safaimitra.safaimitra.repository;

import com.safaimitra.safaimitra.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    boolean existsByEmail(String email);

    // ❌ DELETE THIS METHOD - No 'active' field in User
    // long countByActive(boolean active);

    // ✅ If you need active users count, use this:
    // long countByEnabled(boolean enabled);
}