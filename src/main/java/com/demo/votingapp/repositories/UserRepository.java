package com.demo.votingapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.votingapp.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);
}