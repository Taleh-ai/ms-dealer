package com.example.msdealer.repository;

import com.example.msdealer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
