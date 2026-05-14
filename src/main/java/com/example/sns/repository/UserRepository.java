package com.example.sns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sns.entity.User;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
