package com.example.Dobara1.usermaster;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserMasterEntity, Integer> {
    Optional<UserMasterEntity> findByEmailAndPassword(String email , String password);
//    Optional<UserMasterEntity> existsByEmail(String email);
Optional<UserMasterEntity> findByEmail(String email);
}
